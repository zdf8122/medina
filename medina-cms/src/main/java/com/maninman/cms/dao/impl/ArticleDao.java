package com.maninman.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.maninman.cms.dao.IArticleDao;
import com.maninman.cms.domain.Article;

@Repository
public class ArticleDao extends SqlSessionDaoSupport implements IArticleDao {

	@Override
	public Article getById(String id) {
		return (Article) this.getSqlSession().selectOne("article.getbyid",id);
	}

	@Override
	public Article get(String channel, String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("channel", channel);
		map.put("id", id);
		return (Article) this.getSqlSession().selectOne("article.get",map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getPageArticles(String offset,
			String pageArticlesNumber) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("offset", Integer.parseInt(offset));
		map.put("pageArticlesNumber", Integer.parseInt(pageArticlesNumber));
		return this.getSqlSession().selectList("article.getPageArticles", map);

	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return (Integer) this.getSqlSession().selectOne("article.getTotal");
	}

	@Override
	public void save(Article article) {
		this.getSqlSession().insert("article.save", article);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getByTitle(String title) {
		return this.getSqlSession().selectList("getbytitle", title);
	}

}
