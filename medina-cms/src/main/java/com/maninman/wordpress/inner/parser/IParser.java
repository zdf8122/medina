package com.maninman.wordpress.inner.parser;

import com.maninman.cms.domain.Article;

public interface IParser {

	public Article parse(String url);
}
