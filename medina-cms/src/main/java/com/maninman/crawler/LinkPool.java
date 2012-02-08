package com.maninman.crawler;

import java.util.List;

import com.maninman.crawler.entity.UrlInfo;
import com.maninman.crawler.entity.UrlTypeEnum;

/**
 * 需要改造，把url的处理逻辑封装在这个类里
 * @varsion 1.0
 * @author 徐涛
 * @createtime 2011-12-18 下午10:46:03
 */
public interface LinkPool {

	public List<UrlInfo> popNewUrl(UrlTypeEnum type, int count);
	
	public long pushNewUrl(UrlTypeEnum type, String url);

	public long pushDoneUrl(UrlTypeEnum type, String url);
	
	public boolean isDoneUrl(UrlTypeEnum type, String url);
	public String addUrlInfo(UrlInfo urlInfo);

}
