package com.maninman.cms.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.maninman.Define;
import com.maninman.cms.domain.Article;
import com.maninman.cms.service.IArticleService;

@Controller
public class RestArticleController {

	final static Log log = LogFactory.getLog(RestArticleController.class);
	@Resource
	private IArticleService articleService;

	@RequestMapping(value = "/article/{channel}/{id}", method = RequestMethod.GET)
	public ModelAndView getArticle(@PathVariable("channel") String channel,
			@PathVariable("id") String id, ModelMap map) throws IOException {
		System.out.println(channel);
		System.out.println(id);

		ModelAndView mav = new ModelAndView("article");

		Article article = articleService.get(channel, id);

		if (article == null) {
			article = new Article();
			article.setContent("404");
		}
		mav.addObject("article", article);
		return mav;

	}

	@RequestMapping(value = "/page/{number}", method = RequestMethod.GET)
	public ModelAndView getMultiArticles(@PathVariable("number") String number,
			ModelMap map) throws IOException {

		ModelAndView mav = new ModelAndView("main");

		List<Article> articles = articleService.getPageArticles(number);

		if (articles == null || articles.size() == 0) {
			Article article = new Article();
			article.setContent("404");
			articles = new ArrayList<Article>();
		}

		// 处理文章长度
		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			Document doc = Jsoup.parse(article.getContent());
			Elements es = doc.select("*");

			String digest = null;
			if (es.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (Element e : es) {
					sb.append(e.text());
				}
				digest = sb.toString();

			} else {
				digest = article.getContent();
			}

			int digestLength = digest.length() < Define.DIGEST_SIZE ? digest
					.length() : Define.DIGEST_SIZE;

			digest = digest.substring(0, digestLength);
			article.setContent(digest);
			articles.set(i, article);
		}

		// 处理分页
		int currentPage = Integer.parseInt(number);

		int totalArticles = articleService.getTotal();
		int totalPages = totalArticles / Define.PAGE_ARTICLES_NUMBER;
		if (totalArticles % Define.PAGE_ARTICLES_NUMBER > 0) {
			totalPages += 1;
		}

		int prevPage;
		if (currentPage == 1) {
			prevPage = 0;
		} else {
			prevPage = currentPage - 1;
		}
		int nextPage;
		if (currentPage == totalPages) {
			nextPage = 0;
		} else {
			nextPage = currentPage + 1;
		}

		int halfPageViewSize = Define.PAGE_VIEW_SIZE / 2;
		int startPage;
		if (currentPage < (halfPageViewSize + 1) || totalPages <= Define.PAGE_VIEW_SIZE) {
			startPage = 1;
		} else if (currentPage > (totalPages - halfPageViewSize) && totalPages > Define.PAGE_VIEW_SIZE) {
			startPage = totalPages - Define.PAGE_VIEW_SIZE +1;
		} else {
			startPage = currentPage - halfPageViewSize;
		}

		int endPage;
		if (totalPages <= Define.PAGE_VIEW_SIZE) {
			endPage = totalPages;
		} else if (currentPage > (totalPages - halfPageViewSize) && totalPages > Define.PAGE_VIEW_SIZE) {
			endPage = totalPages;
		} else {
			if (currentPage < (halfPageViewSize+1)) {
				endPage = Define.PAGE_VIEW_SIZE;
			} else {
				endPage = currentPage + halfPageViewSize;
			}
		}

		log.debug("S : " + startPage);
		log.debug("E : " + endPage);

		Map<String, String> pageMap = new HashMap<String, String>();

		pageMap.put("startPage", String.valueOf(startPage));
		pageMap.put("currentPage", String.valueOf(currentPage));
		pageMap.put("endPage", String.valueOf(endPage));
		pageMap.put("totalPages", String.valueOf(totalPages));

		pageMap.put("prevPage", String.valueOf(prevPage));
		pageMap.put("nextPage", String.valueOf(nextPage));
		mav.addObject("articles", articles);
		mav.addObject("page", pageMap);
		return mav;

	}
}
