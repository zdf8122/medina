package com.maninman.cms.service;

import java.util.List;

import com.maninman.cms.domain.Article;

public interface IArticleService {

	public Article getById(String id);
	
	public Article get(String channel, String id);
	
	public List<Article> getPageArticles(String page);
	
	public int getTotal();

	public boolean has(Article a);

	public void save(Article a);
}
