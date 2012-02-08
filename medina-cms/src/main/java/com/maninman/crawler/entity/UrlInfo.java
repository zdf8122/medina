package com.maninman.crawler.entity;

import java.io.Serializable;

public class UrlInfo implements Serializable {

	private static final long serialVersionUID = 1750080247791624808L;
	private String url;
	private String purl;
	private UrlTypeEnum type;
	
	public UrlTypeEnum getType() {
		return type;
	}
	public void setType(UrlTypeEnum type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPurl() {
		return purl;
	}
	public void setPurl(String purl) {
		this.purl = purl;
	}

	
	
}
