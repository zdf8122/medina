package com.maninman.cms.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.maninman.cms.domain.Article;

@Controller
public class PostCommandController {

	@RequestMapping(value = "/article/post", method={RequestMethod.POST})
	public String postArticle(String url, Book book){
		System.out.println(book.getSurl());
		System.out.println(book.getUrl());
		System.out.println(url);

		return book.getUrl();
	}
	
	@ModelAttribute("article")
	public Article getArticle(){
		Article article = new Article();
		article.setContent("content");
		article.setTitle("title");
		return article;
	}
}
class Book{
	private String url;
	private String surl;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSurl() {
		return surl;
	}
	public void setSurl(String surl) {
		this.surl = surl;
	}
	
}