package com.maninman.wordpress.inner.parser.impl;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.maninman.Define;
import com.maninman.cms.domain.Article;
import com.maninman.wordpress.inner.parser.IParser;

@Component
public class LaoNanRenParser implements IParser {

	final static Log log = LogFactory.getLog(LaoNanRenParser.class);

	final String srcSite = "老男人";

	@Override
	public Article parse(String url) {
		Document doc = null;
		Article article = null;
		doc = autoReconnect(url);

		if (doc != null) {
			// 解析标题
			String title = parseTitle(doc);
			// 解析作者
			String author = parseAuthor(doc);
			// 解析文章内容
			String content = parseContent(doc);

			article = new Article();
			article.setTitle(title);
			article.setAuthor(author);
			article.setContent(content);
			article.setSrcSite(srcSite);
			article.setSrcUrl(url);
			article.setChannel("health");
			article.setCreateTime(new Date(new java.util.Date().getTime()));
			article.setCreateUser("administrator");
		}
		return article;
	}

	private String parseAuthor(Document doc) {
		Elements es = doc.select("div[style^=width:480px]");

		StringBuilder author = new StringBuilder();

		// 只取第一个描述
		if (es.size() > 0) {
			String text = es.get(0).text();
			Pattern p = Pattern.compile("来源：(.*)作者：");
			Matcher m = p.matcher(text);

			if (m.find()) {
				author.append(m.group(1).trim());
			} else {
				author.append(srcSite);
			}
		}

		return author.toString();
	}

	private String parseTitle(Document doc) {
		Elements es = doc.select("h1.aTitle");

		StringBuilder title = new StringBuilder();
		for (Element e : es) {
			title.append(e.text());
		}
		return title.toString();
	}

	private String parseContent(Document doc) {
		StringBuilder content = parsePageContent(doc);

		// 处理有分页的情况
		Elements pagination = doc.select(".pager > a");
		List<String> list = new ArrayList<String>();
		for (Element e : pagination) {
			String href = e.attr("abs:href");
			if (!list.contains(href) && !href.trim().equals("") ) {
				list.add(href);
			}
		}

		//page like:http://www.laonanren.com/news/2011-11/46469p2.htm
		Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(String str1, String str2) {
				int v1 = Integer.parseInt(str1.substring(str1
						.lastIndexOf("p") + 1, str1.lastIndexOf(".")));
				int v2 = Integer.parseInt(str2.substring(str2
						.lastIndexOf("p") + 1, str2.lastIndexOf(".")));
				return v1 - v2;
			}
		});
		String lastPage = list.get(list.size()-1);
		int pageNumber = Integer.parseInt(lastPage.substring(lastPage
				.lastIndexOf("p") + 1, lastPage.lastIndexOf(".")));
		list.clear();
		for(int i =2; i<=pageNumber;i++){
			String s = lastPage.substring(0,lastPage
					.lastIndexOf("p")+1) + i + ".htm";
			list.add(s);
		}
		for (String url : list) {
			System.out.println(url);
			doc = autoReconnect(url);
			content.append(parsePageContent(doc));
		}

		return content.toString();
	}

	/*
	 * 解析分页的内容
	 * 思路1：按照制定的规则获取内容
	 * 思路2：得到内容，过滤掉无效内容
	 */
	private StringBuilder parsePageContent(Document doc) {
		Elements es = doc.select("#content ");

		es.attr("id", UUID.randomUUID().toString());
		
		StringBuilder content = new StringBuilder();
		for (Element e : es) {
			Elements img = e.select("img");
			if (img == null || img.size() == 0) {
				content.append("<p>");
				content.append(e.text());
				content.append("</p>");
			} else {
				String url = img.get(0).attr("abs:src");
				e.select("img").attr("src", url);
				content.append(e.toString());
			}
		}

		return content;
	}

	private Document autoReconnect(String url) {
		boolean fail = true;

		int reTimes = 1;

		Document document = null;

		while (document == null && reTimes < Define.MAX_RECONNECT_TIMES) {
			try {
				document = Jsoup.connect(url).get();
				fail = false;
			} catch (IOException e1) {
				log.info(e1);
			}

			if (fail) {
				try {
					TimeUnit.SECONDS.sleep(Define.SLEEP_INTERVAL * reTimes);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		return document;
	}
}
