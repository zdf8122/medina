package com.maninman.crawler;

import com.maninman.crawler.entity.ProcessRequst;
import com.maninman.crawler.entity.ProcessResponse;

public interface Filter {

	void doFilter(ProcessRequst processRequest,ProcessResponse processResponse );
}
