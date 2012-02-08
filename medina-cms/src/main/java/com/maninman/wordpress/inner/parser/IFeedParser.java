package com.maninman.wordpress.inner.parser;

import java.util.List;

public interface IFeedParser {

	List<String> parse(String url);
}
