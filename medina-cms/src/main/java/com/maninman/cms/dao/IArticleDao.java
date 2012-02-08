package com.maninman.cms.dao;

import java.util.List;

import com.maninman.cms.domain.Article;

public interface IArticleDao {

	public void save(Article article);
	
	public Article getById(String id);
	
	public List<Article> getByTitle(String title);
	
	public Article get(String channel, String id);
	
	public List<Article> getPageArticles(String offset, String pageArticlesNumber);
	
	public int getTotal();
}
