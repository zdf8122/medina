package com.maninman.wordpress;

import java.net.MalformedURLException;
import java.util.List;
import java.util.TimerTask;

import javax.annotation.Resource;

import net.bican.wordpress.Page;
import net.bican.wordpress.Wordpress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import redstone.xmlrpc.XmlRpcFault;

import com.maninman.cms.domain.Article;
import com.maninman.wordpress.inner.parser.IFeedParser;
import com.maninman.wordpress.inner.parser.IParser;

@Component
public class LaoNanRenTask extends TimerTask{

	final static Log log = LogFactory.getLog(LaoNanRenTask.class);
	
	final String feedUrl = "http://www.laonanren.com/health/index.htm";
	
	final String womanFeedUrl = "http://www.laonanren.com/female/indexp3.htm";
	
	@Resource
	IParser laoNanRenParser;
	
	@Resource
	IFeedParser laoNanRenFeedParser;
	
	@Resource
	IFeedParser laoNanRenWomanFeedParser;
	
	public IParser getLaoNanRenParser() {
		return laoNanRenParser;
	}

	public void setLaoNanRenParser(IParser laoNanRenParser) {
		this.laoNanRenParser = laoNanRenParser;
	}

	public IFeedParser getLaoNanRenFeedParser() {
		return laoNanRenFeedParser;
	}

	public void setLaoNanRenFeedParser(IFeedParser laoNanRenFeedParser) {
		this.laoNanRenFeedParser = laoNanRenFeedParser;
	}

	public IFeedParser getLaoNanRenWomanFeedParser() {
		return laoNanRenWomanFeedParser;
	}

	public void setLaoNanRenWomanFeedParser(IFeedParser laoNanRenWomanFeedParser) {
		this.laoNanRenWomanFeedParser = laoNanRenWomanFeedParser;
	}

	
	@Override
	public void run() {
		
		String username = "admin";
	    String password = "xutao1983";
	    String xmlRpcUrl = "http://360male.com/xmlrpc.php";
	    try {
			Wordpress wp = new Wordpress(username, password, xmlRpcUrl);
			// = laoNanRenFeedParser.parse(feedUrl);
			
			 List<String> urls = laoNanRenWomanFeedParser.parse(womanFeedUrl);
			
			for(String url:urls){
				Article a = laoNanRenParser.parse(url);
				
				Page page = new Page();
				page.setTitle(a.getTitle());
				page.setDescription(a.getContent());
				String id = wp.newPost(page, true);
				log.debug("PARSE : " + a.getTitle()+ " " + id);
			}
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		} catch (XmlRpcFault e) {
			e.printStackTrace();
		}
	    	
	}

}
