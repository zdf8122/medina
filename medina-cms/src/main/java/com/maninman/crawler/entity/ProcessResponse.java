package com.maninman.crawler.entity;

import java.util.List;

public class ProcessResponse {

	private List<UrlInfo> linkUrlInfos;
	private List<UrlInfo> pageUrlInfos;
	public List<UrlInfo> getLinkUrlInfos() {
		return linkUrlInfos;
	}
	public void setLinkUrlInfos(List<UrlInfo> linkUrlInfos) {
		this.linkUrlInfos = linkUrlInfos;
	}
	public List<UrlInfo> getPageUrlInfos() {
		return pageUrlInfos;
	}
	public void setPageUrlInfos(List<UrlInfo> pageUrlInfos) {
		this.pageUrlInfos = pageUrlInfos;
	}
	
	private RawArticle rawArticle;
	public RawArticle getRawArticle() {
		return rawArticle;
	}
	public void setRawArticle(RawArticle rawArticle) {
		this.rawArticle = rawArticle;
	}
	
	
	
}
