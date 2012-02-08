package com.maninman.crawler.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.maninman.crawler.entity.UrlInfo;
import com.maninman.crawler.entity.UrlTypeEnum;

public class DefaultLinkPoolTest {

	private DefaultLinkPool pool = new DefaultLinkPool();
	
	UrlInfo info1 = new UrlInfo();
	UrlInfo info2 = new UrlInfo();
	UrlInfo info3 = new UrlInfo();
	
	@Before
	public void setUp() throws Exception {
		pool.init("127.0.0.1", 6379);

		info1.setUrl("http://redis.io/commands/keys/1");
		info1.setType(UrlTypeEnum.LINK);
			
		info2.setUrl("http://redis.io/commands/keys/2");
		info2.setType(UrlTypeEnum.LINK);
		
		info3.setUrl("http://redis.io/commands/keys/3");
		info3.setType(UrlTypeEnum.LINK);
		pool.addUrlInfo(info2);
		pool.addUrlInfo(info3);
	}
	@Test
	public void testaddNewUrl() {
		String str;
		str = pool.addUrlInfo(info1);
		assertEquals("OK", str);
		System.out.println("Add: " + str);
	}

	@Test
	public void testPushAndPopNewLinkUrl() {
		long re = pool.pushNewUrl(UrlTypeEnum.LINK, info2.getUrl());
		assertEquals(1, re);
		re = pool.pushNewUrl(UrlTypeEnum.LINK, info3.getUrl());
		assertEquals(2, re);
		
		List<UrlInfo> urls = pool.popNewUrl(UrlTypeEnum.LINK, 3);
		for(UrlInfo url: urls){
			System.out.println(url.getUrl());
		}
		assertEquals(2, urls.size());
		
		Assert.assertFalse(pool.isDoneUrl(UrlTypeEnum.LINK, info2.getUrl()));
		pool.pushDoneUrl(UrlTypeEnum.LINK, info2.getUrl());
		Assert.assertTrue(pool.isDoneUrl(UrlTypeEnum.LINK, info2.getUrl()));
	}

	@After
	public void destory(){
		String s = pool.clear();
		System.out.println("Destory: " +s);
	}
}
