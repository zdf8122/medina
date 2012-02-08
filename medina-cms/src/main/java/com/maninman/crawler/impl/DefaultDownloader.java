package com.maninman.crawler.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.maninman.crawler.Downloader;
import com.maninman.crawler.entity.Document;

/**
 * 
 * @varsion 1.0
 * @author 徐涛
 * @createtime 2011-12-14 下午02:10:22
 */
public class DefaultDownloader implements Downloader {

	private HttpClient httpclient = new DefaultHttpClient();
	
	/**
	 * 如何把一个获得的流对象转换成内存直接对象
	 */
	@Override
	public Document download(String url) {
		Document doc = new Document();
		try {
			HttpGet httpget = new HttpGet(url);
			HttpResponse response;
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String type = EntityUtils.getContentMimeType(entity);
			doc.setContent(EntityUtils.toString(entity, "gb2312"));
			doc.setDocType(type);
			doc.setUrl(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return doc;
	}

}
