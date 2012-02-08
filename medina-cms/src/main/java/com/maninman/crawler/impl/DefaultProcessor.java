package com.maninman.crawler.impl;

import java.util.Map;

import com.maninman.crawler.Filter;
import com.maninman.crawler.Processor;
import com.maninman.crawler.entity.Document;
import com.maninman.crawler.entity.ProcessRequst;
import com.maninman.crawler.entity.ProcessResponse;

public class DefaultProcessor implements Processor{

	/**
	 * 过滤器集合
	 */
	private Map<String,Filter> filters;
	
	public Map<String, Filter> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Filter> filters) {
		this.filters = filters;
	}

	private ProcessRequst processRequst;
	private ProcessResponse processResponse;
	
	public ProcessRequst getProcessRequst() {
		return processRequst;
	}

	public void setProcessRequst(ProcessRequst processRequst) {
		this.processRequst = processRequst;
	}

	public ProcessResponse getProcessResponse() {
		return processResponse;
	}

	public void setProcessResponse(ProcessResponse processResponse) {
		this.processResponse = processResponse;
	}

	@Override
	public void process(Document doc) {
		for(String key:filters.keySet()){
			Filter filter = filters.get(key);
			this.processRequst.setDocument(doc);
			filter.doFilter(this.processRequst,this.processResponse);
		}
		
	}

	@Override
	public boolean addFilter(String name, Filter filter) {
		if(name != null || filter == null){
			return false;
		}
		filters.put(name, filter);
		return true;
	}

}
