package com.maninman.crawler.impl;

import java.util.List;

import com.maninman.crawler.Filter;
import com.maninman.crawler.LinkPool;
import com.maninman.crawler.entity.ProcessRequst;
import com.maninman.crawler.entity.ProcessResponse;
import com.maninman.crawler.entity.UrlInfo;

public class LinkPersistFilter implements Filter {

	private LinkPool linkPool;

	public LinkPool getLinkPool() {
		return linkPool;
	}

	public void setLinkPool(LinkPool linkPool) {
		this.linkPool = linkPool;
	}

	/**
	 * 需要改造成可配置的形式
	 */
	@Override
	public void doFilter(ProcessRequst processRequest,
			ProcessResponse processResponse) {
		List<UrlInfo> urlInfos = processResponse.getLinkUrlInfos();
		if (urlInfos != null) {
			for (UrlInfo urlInfo : urlInfos) {
				linkPool.addUrlInfo(urlInfo);
			}
		}
		urlInfos = processResponse.getPageUrlInfos();
		if (urlInfos != null) {
			for (UrlInfo urlInfo : urlInfos) {
				linkPool.addUrlInfo(urlInfo);
			}
		}
	}
}
