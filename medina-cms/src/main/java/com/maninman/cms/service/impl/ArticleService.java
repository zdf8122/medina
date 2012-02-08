package com.maninman.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maninman.Define;
import com.maninman.cms.dao.IArticleDao;
import com.maninman.cms.domain.Article;
import com.maninman.cms.service.IArticleService;

@Service
@Transactional
public class ArticleService implements IArticleService{

	@Resource
	private IArticleDao articleDao;
	
	
	@Override
	public Article getById(String id) {
		return articleDao.getById(id);
	}


	@Override
	public Article get(String channel, String id) {
		return articleDao.get(channel, id);
	}


	@Override
	public List<Article> getPageArticles(String page) {
		
		int pageNumber = Integer.parseInt(page);
		
		String offset = null;
		if(pageNumber>1){
			offset = String.valueOf((pageNumber - 1)*Define.PAGE_ARTICLES_NUMBER);
		}else{
			offset = String.valueOf(0);
		}
		return articleDao.getPageArticles(offset, String.valueOf(Define.PAGE_ARTICLES_NUMBER));
	}


	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return articleDao.getTotal();
	}


	@Override
	public boolean has(Article a) {
		if(articleDao.getByTitle(a.getTitle()).size() > 0){
			return true;
		}
		return false;
	}


	@Override
	public void save(Article a) {
		articleDao.save(a);
	}

}
