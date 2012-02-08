package com.maninman.crawler.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import redis.clients.jedis.Jedis;

import com.maninman.crawler.LinkPool;
import com.maninman.crawler.entity.UrlInfo;
import com.maninman.crawler.entity.UrlTypeEnum;
import com.maninman.utils.Converter;

public class DefaultLinkPool implements LinkPool {

	private String newLinkPrefix = "NEW";
	private String doneLinkPrefix = "DONE";
	private Jedis jedis;

	public void init(String host, int port) {
		jedis = new Jedis(host, port);
	}

	public List<UrlInfo> popNewUrl(UrlTypeEnum type, int count) {
		List<String> urlHashes = null;

		urlHashes = jedis.lrange(newLinkPrefix + type.toString(), 0, count - 1);

		List<UrlInfo> urls = new ArrayList<UrlInfo>();
		for (String hash : urlHashes) {
			byte[] bytes = jedis.get(hash.getBytes());
			Object value = null;
			try {
				value = Converter.getObjectFromBytes(bytes);
				UrlInfo info = (UrlInfo) value;
				urls.add(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return urls;
	}

	@Override
	public long pushNewUrl(UrlTypeEnum type, String url) {
		long re = jedis.lpush(newLinkPrefix + type.toString(),
				String.valueOf(url.hashCode()));
		return re;
	}

	@Override
	public long pushDoneUrl(UrlTypeEnum type, String url) {
		return jedis.hset(doneLinkPrefix + type.toString(),
				String.valueOf(url.hashCode()), new Date().toString());
	}

	@Override
	public boolean isDoneUrl(UrlTypeEnum type, String url) {
		String re = jedis.hget(doneLinkPrefix + type.toString(),
				String.valueOf(url.hashCode()));
		if (re != null) {
			return true;
		}
		return false;
	}

	@Override
	public String addUrlInfo(UrlInfo urlInfo) {
		String re = null;
		try {
			String formRedis = jedis
					.get(String.valueOf(urlInfo.getUrl().hashCode()));
			if (formRedis == null || formRedis.equals("nil")) {
				if (urlInfo.getUrl() != null && !urlInfo.getUrl().equals("")) {
					re = jedis.set(String.valueOf(urlInfo.getUrl().hashCode())
							.getBytes(), Converter.getBytesFromObject(urlInfo));
					this.pushNewUrl(urlInfo.getType(), urlInfo.getUrl());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public String clear() {
		return jedis.flushDB();
	}

}
