package com.maninman.crawler.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.maninman.crawler.entity.Document;

public class DefaultDownloaderTest {

	DefaultDownloader downloader ;
	@Before
	public void setUp() throws Exception {
		downloader =  new DefaultDownloader();
	}

	@Test
	public void testDownloadPage() {
		String url = "http://www.5542.com";
		Document doc = downloader.download(url);
		
		Assert.assertEquals("text/html",doc.getDocType());
		
//		try {
//			System.out.println(new String(doc.getContent(),"gb2312"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
	}
	
	@Test
	public void testDownloadImg() {
		String url = "http://img2.5542.com/Upload2010/46/lsggsntsz/31.jpg";
		Document doc = downloader.download(url);

		Assert.assertEquals("image/jpeg",doc.getDocType());
	
			
	}


}
