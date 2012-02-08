package com.maninman.crawler.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.maninman.crawler.Filter;
import com.maninman.crawler.entity.Document;
import com.maninman.crawler.entity.ProcessRequst;
import com.maninman.crawler.entity.ProcessResponse;
import com.maninman.crawler.entity.RawArticle;

public class PageParseFilter implements Filter {

	private List<String> pageQueries;
	
	private String spiltPageQuery;
	
	public String getSpiltPageQuery() {
		return spiltPageQuery;
	}

	public void setSpiltPageQuery(String spiltPageQuery) {
		this.spiltPageQuery = spiltPageQuery;
	}

	public List<String> getPageQueries() {
		return pageQueries;
	}

	public void setPageQueries(List<String> pageQueries) {
		this.pageQueries = pageQueries;
	}

	@Override
	public void doFilter(ProcessRequst processRequest,
			ProcessResponse processResponse) {
		try {
			Document doc = processRequest.getDocument();
			org.jsoup.nodes.Document document = Jsoup.parse(doc.getContent(),
					doc.getUrl());
		
			StringBuilder content = new StringBuilder();
			
			RawArticle rawArticle  = new RawArticle();
			for (String xpath : pageQueries) {
				List<Element> list = document.select(xpath);	
				for (Element el : list) {
					content.append(el.toString());
				}
			}
			//处理分页
			String nextContent = parseSplitPage(document);
			content.append(nextContent);
			
			rawArticle.setContent(content.toString());
			rawArticle.setCreateTime(new Timestamp(new Date().getTime()));
			rawArticle.setUrl(doc.getUrl());
			
			processResponse.setRawArticle(rawArticle);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private String parseSplitPage(org.jsoup.nodes.Document doc){
		StringBuilder content =  new StringBuilder();
		Elements pagination = doc.select(spiltPageQuery);
		List<String> list = new ArrayList<String>();
		for (Element e : pagination) {
			String href = e.attr("abs:href");
			if (!list.contains(href) && !href.trim().equals("")) {
				list.add(href);
			}
		}
		if(list.size() > 1 ){
			String prefix = this.prefix(list.get(0), list.get(1));
			String suffix = this.suffix(list.get(0), list.get(1));
			
			List<Integer> pageNumbers = new ArrayList<Integer>();
			for(String url : list){
				Integer integer = Integer.parseInt(url.replace(prefix, "").replace(suffix, ""));
				pageNumbers.add(integer);
			}
			Collections.sort(pageNumbers);

			int lastPageNumber = pageNumbers.get(pageNumbers.size()-1);
			list.clear();
			for(int i =2; i<=lastPageNumber; i++){
				String s = prefix + i + suffix;
				list.add(s);
			}
		}
		
		for (String url : list) {
			System.out.println(url);
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			for (String xpath : pageQueries) {
				List<Element> elmList = doc.select(xpath);	
				for (Element el : elmList) {
					content.append(el.toString());
				}
			}
		}

		return content.toString();
	}
	
	private String prefix(String str1, String str2){
		int len1 = str1.length();
		int len2 = str2.length();
		int minLen = len1 <= len2? len1:len2;
		for(int i =0; i< minLen;i++){
			if(str1.charAt(i)!= str2.charAt(i)){
				return str1.substring(0, i);
			}
		}		
		return null;
	}
	
	private String suffix(String str1, String str2){
		int len1 = str1.length();
		int len2 = str2.length();
		int minLen = len1 <= len2? len1:len2;
		for(int i = minLen - 1; i > -1; i--){
			if(str1.charAt(i)!= str2.charAt(i)){
				return str1.substring(i+1);
			}
		}	
		return null;
	}
}
	
