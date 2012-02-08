package com.maninman.wordpress.inner.task;

import java.util.List;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.maninman.cms.domain.Article;
import com.maninman.cms.service.IArticleService;
import com.maninman.wordpress.inner.parser.IFeedParser;
import com.maninman.wordpress.inner.parser.IParser;

@Component
public class LaoNanRenTask extends TimerTask{

	final static Log log = LogFactory.getLog(LaoNanRenTask.class);
	
	final String feedUrl = "http://www.laonanren.com/health/index.htm";
	
	final String womanFeedUrl = "http://www.laonanren.com/female/index.htm";
	
	@Resource
	IParser laoNanRenParser;
	
	@Resource
	IFeedParser laoNanRenFeedParser;
	
	@Resource
	IFeedParser laoNanRenWomanFeedParser;
	
	@Resource
	IArticleService articleService;
	
	@Override
	public void run() {
		List<String> urls = laoNanRenFeedParser.parse(feedUrl);
		
		urls.addAll(laoNanRenWomanFeedParser.parse(womanFeedUrl));
		
		for(String url:urls){
			Article a = laoNanRenParser.parse(url);
			log.debug("PARSE : " + a.getTitle());
			if(!articleService.has(a)){
				articleService.save(a);
				log.debug("SAVE : " + a.getTitle());			
			}
			
		}
	}

}
