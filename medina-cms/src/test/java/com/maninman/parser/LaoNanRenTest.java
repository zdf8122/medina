package com.maninman.parser;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.maninman.cms.domain.Article;
import com.maninman.wordpress.inner.parser.impl.LaoNanRenFeedParser;
import com.maninman.wordpress.inner.parser.impl.LaoNanRenParser;
import com.maninman.wordpress.inner.parser.impl.LaoNanRenWomanFeedParser;

public class LaoNanRenTest {

	final static Log log = LogFactory.getLog(LaoNanRenTest.class);
	public static void main(String[] args) {
		//dofeed();
		//parseArticleTest("http://www.laonanren.com/news/2011-11/46329.htm");
		womanFeedTest() ;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static void dofeed() {
		List<String> urls = feedTest();
		
		for(String s: urls){
			parseArticleTest(s);
		}
	}

	/**
	 * 
	 */
	private static void parseArticleTest(String url) {
		LaoNanRenParser parser = new LaoNanRenParser();
		
		Article a = parser.parse(url);
	
		//FTChinaTest.insertArticle(a);
		log.info(a.getTitle());
		log.info(a.getAuthor());
		log.info(a.getContent());
	}

	/**
	 * 
	 */
	private static List<String> feedTest() {
		LaoNanRenFeedParser fp = new LaoNanRenFeedParser();
		
		List<String> list = fp.parse("http://www.laonanren.com/health/index.htm");
		
		
		return list;
	}
	
	private static List<String> womanFeedTest() {
		LaoNanRenWomanFeedParser fp = new LaoNanRenWomanFeedParser();
		
		List<String> urls = fp.parse("http://www.laonanren.com/female/index.htm");
		
		for(String s: urls){
			System.out.println(s);
		}
		return urls;
	}
}
