package com.maninman.crawler.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.maninman.crawler.entity.Document;
import com.maninman.crawler.entity.ProcessRequst;
import com.maninman.crawler.entity.ProcessResponse;
import com.maninman.crawler.entity.UrlInfo;

public class LinkFilterTest {

	private Document doc;
	private LinkParseFilter filter = new LinkParseFilter();

	private LinkPersistFilter perFilter = new LinkPersistFilter();

	@Before
	public void setUp() throws Exception {

		DefaultDownloader downloader = new DefaultDownloader();
		doc = downloader.download("http://www.laonanren.com/female/index.htm");

		List<String> linkXpathes = new ArrayList<String>();
		linkXpathes.add("table[id=dlNews] a");
		filter.setLinkLinkXpathes(linkXpathes);

		List<String> pageXpathes = new ArrayList<String>();
		pageXpathes.add(".pager a");
		filter.setPageLinkXpathes(pageXpathes);
		
		DefaultLinkPool linkPool = new DefaultLinkPool();
		linkPool.init("127.0.0.1", 6379);
		perFilter.setLinkPool(linkPool);
	}

	@Test
	public void testDoFilter() {
		ProcessRequst req = new ProcessRequst();
		ProcessResponse resp = new ProcessResponse();

		req.setDocument(doc);
		filter.doFilter(req, resp);
		perFilter.doFilter(req, resp);
		for (UrlInfo info : resp.getLinkUrlInfos()) {
			System.out.println("link: " + info.getUrl());
		}
		System.out.println("------------------");
		for (UrlInfo info : resp.getPageUrlInfos()) {
			System.out.println("page: " + info.getUrl());
		}
	}

}
