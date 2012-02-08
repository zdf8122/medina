package com.maninman.cms.action.experiment;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.maninman.cms.domain.Article;
import com.maninman.cms.service.IArticleService;

@Controller
public class ArticleController extends AbstractController {

	@Resource
	private IArticleService articleService;
	
	public IArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(IArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("article");

		Article article = articleService.getById("1");

		mav.addObject("article", article);
		return mav;
	}

}
