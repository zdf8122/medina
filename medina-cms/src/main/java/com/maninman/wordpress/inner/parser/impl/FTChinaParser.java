package com.maninman.wordpress.inner.parser.impl;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.maninman.Define;
import com.maninman.cms.domain.Article;
import com.maninman.wordpress.inner.parser.IParser;

public class FTChinaParser implements IParser {

	final static Log log = LogFactory.getLog(FTChinaParser.class);

	final String srcSite = "FT中文网";

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
			article.setChannel("finance");
			article.setCreateTime(new Date(new java.util.Date().getTime()));
			article.setCreateUser("administrator");
		}
		return article;
	}

	private String parseAuthor(Document doc) {
		Elements es = doc.select(".byline > a");

		StringBuilder author = new StringBuilder();

		// 只取第一个描述
		if (es.size() > 0) {
			author.append(es.get(0).text());
		}

		return author.toString();
	}

	private String parseTitle(Document doc) {
		Elements es = doc.select("#topictitle");

		StringBuilder title = new StringBuilder();
		for (Element e : es) {
			title.append(e.text());
		}
		return title.toString();
	}

	private String parseContent(Document doc) {
		StringBuilder content = parsePageContent(doc);

		// 处理有分页的情况
		Elements pagination = doc.select(".pagination > a");
		List<String> list = new ArrayList<String>();
		for (Element e : pagination) {
			if (!list.contains(e.attr("abs:href"))) {
				list.add(e.attr("abs:href"));
			}
		}

		Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(String str1, String str2) {
				int v1 = Integer.parseInt(str1.substring(str1
						.lastIndexOf("page=") + 5));
				int v2 = Integer.parseInt(str2.substring(str2
						.lastIndexOf("page=") + 5));
				return v1 - v2;
			}
		});
		for (String url : list) {
			doc = autoReconnect(url);
			content.append(parsePageContent(doc));
		}

		return content.toString();
	}

	/*
	 * 解析分页的内容
	 */
	private StringBuilder parsePageContent(Document doc) {
		Elements es = doc.select("div > p");

		StringBuilder content = new StringBuilder();
		for (Element e : es) {
			String id = e.id();
			// 文章内容结束于该id
			if (id != null && id.equals("show_sync_weibo")) {
				break;
			}
			content.append(e.toString());
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
