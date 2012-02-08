package com.maninman.wordpress;

import java.net.MalformedURLException;

import net.bican.wordpress.Page;
import net.bican.wordpress.Wordpress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redstone.xmlrpc.XmlRpcFault;

import com.maninman.cms.domain.Article;
import com.maninman.wordpress.inner.parser.IParser;
import com.maninman.wordpress.inner.parser.impl.LaoNanRenParser;

public class TempForSite {

	final static Log log = LogFactory.getLog(TempForSite.class);

	public static boolean addArticle(String url) {
		String username = "admin";
		String password = "xutao1983";
		String xmlRpcUrl = "http://360male.com/xmlrpc.php";
		try {
			Wordpress wp = new Wordpress(username, password, xmlRpcUrl);
			// = laoNanRenFeedParser.parse(feedUrl);
			IParser laoNanRenParser = new LaoNanRenParser();
			Article a = laoNanRenParser.parse(url);

			System.out.println(a.getContent());
			Page page = new Page();
			page.setTitle(a.getTitle());
			page.setDescription(a.getContent());
			String id = wp.newPost(page, true);
			log.debug("PARSE : " + a.getTitle() + " " + id);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (XmlRpcFault e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static void main(String[] args) {
		String url ="http://www.laonanren.com/news/2011-12/49025.htm";
		TempForSite.addArticle(url);
	}
}
