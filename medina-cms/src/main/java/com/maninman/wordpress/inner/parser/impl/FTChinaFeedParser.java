package com.maninman.wordpress.inner.parser.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.maninman.Define;
import com.maninman.wordpress.inner.parser.IFeedParser;

public class FTChinaFeedParser implements IFeedParser {

	final static Log log = LogFactory.getLog(FTChinaFeedParser.class);
	
	@Override
	public List<String> parse(String url) {

		List<String> urls = new ArrayList<String>();

		Document document = autoReconnect(url);
		
		@SuppressWarnings("unchecked")
		List<Node> list = document.selectNodes("//item/link");

		for (Node n : list) {
			urls.add(n.getText());
		}

		return urls;
	}
	
	private Document autoReconnect(String url){
		boolean fail = true;
		
		int reTimes = 1;
		SAXReader saxReader = new SAXReader();
		Document document = null;
		
		while(document == null && reTimes < Define.MAX_RECONNECT_TIMES){
			try {
				document = saxReader.read(new URL(url));
				fail = false;
			} catch (MalformedURLException e) {
				log.info(e);
			} catch (DocumentException e) {
				log.info(e);
			}
			
			if(fail){
				try {
					TimeUnit.SECONDS.sleep(Define.SLEEP_INTERVAL*reTimes);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	
		return document;
	}

}
