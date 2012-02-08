package com.maninman.crawler.impl;

import com.maninman.crawler.Filter;
import com.maninman.crawler.entity.ProcessRequst;
import com.maninman.crawler.entity.ProcessResponse;

public class PagePersistFilter implements Filter{

	private RawArticleDao rawArticleDao;
	
	public RawArticleDao getRawArticleDao() {
		return rawArticleDao;
	}

	public void setRawArticleDao(RawArticleDao rawArticleDao) {
		this.rawArticleDao = rawArticleDao;
	}

	@Override
	public void doFilter(ProcessRequst processRequest,
			ProcessResponse processResponse) {
		rawArticleDao.save(processResponse.getRawArticle());
		
	}

}
