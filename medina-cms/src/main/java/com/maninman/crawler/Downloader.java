package com.maninman.crawler;

import com.maninman.crawler.entity.Document;

public interface Downloader {
	
	public Document download(String url);
}
