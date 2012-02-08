package com.maninman.crawler;

import java.util.List;

import com.maninman.crawler.entity.Document;
import com.maninman.crawler.entity.UrlInfo;
import com.maninman.crawler.entity.UrlTypeEnum;
import com.maninman.crawler.impl.DefaultLinkPool;


/**
 * 
 * @{#} Spider.java Create on 2011-12-14 下午01:55:45    
 *    
 * 类功能说明:   爬虫主体逻辑
 *
 * @Version 1.0
 * @Author colin
 */

public class Spider {

	//private Context context;
	private DefaultLinkPool linkPool;
	
	private Downloader downloader;
	private Processor processor;
	
	/**
	 * TODO 如何实现link与page解析策略的可定制
	 */
	public void run(){
		
		while(true){
			List<UrlInfo> urls = linkPool.popNewUrl(UrlTypeEnum.LINK, 10);
			for(UrlInfo url:urls){
				Document doc = downloader.download(url.getUrl());
				processor.process(doc);
			}
		}
	}
}
