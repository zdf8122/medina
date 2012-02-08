package com.maninman.crawler.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.maninman.crawler.Filter;
import com.maninman.crawler.entity.Document;
import com.maninman.crawler.entity.ProcessRequst;
import com.maninman.crawler.entity.ProcessResponse;
import com.maninman.crawler.entity.UrlInfo;
import com.maninman.crawler.entity.UrlTypeEnum;

public class LinkParseFilter implements Filter {

	private List<String> linkLinkXpathes;
	private List<String> pageLinkXpathes;

	public List<String> getLinkLinkXpathes() {
		return linkLinkXpathes;
	}

	public void setLinkLinkXpathes(List<String> linkLinkXpathes) {
		this.linkLinkXpathes = linkLinkXpathes;
	}

	public List<String> getPageLinkXpathes() {
		return pageLinkXpathes;
	}

	public void setPageLinkXpathes(List<String> pageLinkXpathes) {
		this.pageLinkXpathes = pageLinkXpathes;
	}

	/**
	 * 对于link page等url的处理逻辑类似，要重构合并代码
	 */
	@Override
	public void doFilter(ProcessRequst processRequest,
			ProcessResponse processResponse) {
		try {
			Document doc = processRequest.getDocument();
			org.jsoup.nodes.Document document = Jsoup.parse(doc.getContent(),
					doc.getUrl());

			List<UrlInfo> linkUrlInfos =  processResponse.getLinkUrlInfos();
			
			if(linkUrlInfos == null){
				linkUrlInfos = new ArrayList<UrlInfo>();
			}
			for (String xpath : linkLinkXpathes) {
				List<Element> list = document.select(xpath);
				for (Element el : list) {
					String newUrl = el.absUrl("href");
					UrlInfo info = new UrlInfo();
					info.setUrl(newUrl);
					info.setType(UrlTypeEnum.LINK);
					info.setPurl(doc.getUrl());
				
					linkUrlInfos.add(info);

				}
			}
			processResponse.setLinkUrlInfos(linkUrlInfos);
			
			List<UrlInfo> pageUrlInfos =  processResponse.getPageUrlInfos();
			
			if(pageUrlInfos == null){
				pageUrlInfos = new ArrayList<UrlInfo>();
			}
			for (String xpath : pageLinkXpathes) {
				List<Element> list = document.select(xpath);
				for (Element el : list) {
					String newUrl = el.absUrl("href");
					UrlInfo info = new UrlInfo();
					info.setUrl(newUrl);
					info.setType(UrlTypeEnum.PAGE);
					info.setPurl(doc.getUrl());
				
					pageUrlInfos.add(info);

				}
			}
			processResponse.setPageUrlInfos(pageUrlInfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
