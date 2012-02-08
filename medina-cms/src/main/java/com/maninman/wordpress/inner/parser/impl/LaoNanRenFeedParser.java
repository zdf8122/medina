package com.maninman.wordpress.inner.parser.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.maninman.Define;
import com.maninman.wordpress.inner.parser.IFeedParser;

@Component
public class LaoNanRenFeedParser implements IFeedParser{

	final static Log log = LogFactory.getLog(LaoNanRenFeedParser.class);
	
	@Override
	public List<String> parse(String url) {
		Document doc = null;
		
		doc = autoReconnect(url);

		List<String> list = new ArrayList<String>();
		
		Elements es = doc.select("div.title a");
		
		for(Element e:es){
			list.add(e.attr("abs:href"));
		}
		return list;
	}

	protected Document autoReconnect(String url) {
		boolean fail = true;

		int reTimes = 1;

		Document document = null;

		while (document == null && reTimes < Define.MAX_RECONNECT_TIMES) {
			try {
				document = Jsoup.connect(url).get();
				fail = false;
			} catch (IOException e1) {
				log.info(e1);
			}

			if (fail) {
				try {
					TimeUnit.SECONDS.sleep(Define.SLEEP_INTERVAL * reTimes);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		return document;
	}
}
