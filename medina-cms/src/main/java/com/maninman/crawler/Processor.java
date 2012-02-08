package com.maninman.crawler;

import com.maninman.crawler.entity.Document;

public interface Processor {

	public void process(Document doc);
	
	public boolean addFilter(String name, Filter filter);
}
