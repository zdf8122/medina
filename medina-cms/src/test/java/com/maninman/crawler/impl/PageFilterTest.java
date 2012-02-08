package com.maninman.crawler.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.maninman.crawler.entity.Document;
import com.maninman.crawler.entity.ProcessRequst;
import com.maninman.crawler.entity.ProcessResponse;

public class PageFilterTest {

	private Document doc;
	private PageParseFilter pageFilter = new PageParseFilter();
	private PagePersistFilter pagePersistFilter = new PagePersistFilter();
	@Before
	public void setUp() throws Exception {
		DefaultDownloader downloader = new DefaultDownloader();
		doc = downloader.download("http://www.laonanren.com/news/2011-12/49012.htm");
		
		List<String> pageQueries = new ArrayList<String>();
		pageQueries.add("#content");
		pageFilter.setPageQueries(pageQueries);
		pageFilter.setSpiltPageQuery(".pager > a");
		
		RawArticleDao dao = new RawArticleDao();
		pagePersistFilter.setRawArticleDao(dao);
		
	}

	@Test
	public void testDoFilter() {
		ProcessRequst req = new ProcessRequst();
		ProcessResponse resp = new ProcessResponse();

		req.setDocument(doc);
		
		pageFilter.doFilter(req, resp);
		pagePersistFilter.doFilter(req, resp);
		System.out.println(resp.getRawArticle().getUrl());
		System.out.println(resp.getRawArticle().getContent());
	}

}
