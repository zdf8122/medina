package com.maninman.wordpress.inner.parser.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class LaoNanRenWomanFeedParser extends LaoNanRenFeedParser {

	@Override
	public List<String> parse(String url) {
		Document doc = null;

		doc = autoReconnect(url);

		List<String> list = new ArrayList<String>();

		Elements es = doc.select("tbody>tr>td>a");

		for (Element e : es) {
			list.add(e.attr("abs:href"));
		}
		return list;

	}

}
