package com.maninman.cms.action.experiment;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.maninman.cms.domain.Article;

@Controller
public class MainPageController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("main");
		
		List<Article> list = new ArrayList<Article>();
		
		Article a1 = new Article();
		a1.setTitle("算命人的商业头脑");
		a1.setContent("今天在重庆街上看到有一个道士模样的人， 地上一张海报，标题是：哑巴测量算命。 大概意思是：本人是来自五台山第十三代传人，测量算命，造福大众，不要钱。 街上算命不稀奇，但不要钱，这个就很吸引人了。 所以周围围了一圈人，都在看。 我本来也没什么兴趣，但我就感到奇怪， 难道真有");
		
		Article a2 = new Article();
		a2.setTitle("t2");
		a2.setContent("c2");
		
		Article a3 = new Article();
		a3.setTitle("t3");
		a3.setContent("c3");
		
		Article a4 = new Article();
		a4.setTitle("t4");
		a4.setContent("c4");

		//list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		
		mav.addObject("article", a1);
		mav.addObject("articleListCache", list);
		return mav;
	}

}
