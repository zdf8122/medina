package com.maninman.wordpress;

import java.util.Timer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.maninman.wordpress.inner.parser.IFeedParser;
import com.maninman.wordpress.inner.parser.IParser;
import com.maninman.wordpress.inner.parser.impl.LaoNanRenFeedParser;
import com.maninman.wordpress.inner.parser.impl.LaoNanRenParser;
import com.maninman.wordpress.inner.parser.impl.LaoNanRenWomanFeedParser;


@Component
public class Schedule {

	long interval = 6L * 60 * 60 * 1000;
	
	long delay = 1L * 1000;
	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	@Resource
	LaoNanRenTask laoNanRenTask;
	
	@PostConstruct
	public void init(){
		Timer timer = new Timer();	
		
		LaoNanRenTask laoNanRenTask = new LaoNanRenTask();
		IParser laoNanRenParser = new LaoNanRenParser();
		IFeedParser laoNanRenFeedParser = new LaoNanRenFeedParser();
		IFeedParser laoNanRenWomanFeedParser = new LaoNanRenWomanFeedParser();
		laoNanRenTask.setLaoNanRenParser(laoNanRenParser);
		laoNanRenTask.setLaoNanRenFeedParser(laoNanRenFeedParser);
		laoNanRenTask.setLaoNanRenWomanFeedParser(laoNanRenWomanFeedParser);
		
		timer.schedule( laoNanRenTask , delay, interval);
	}
	
	public static void main(String[] args) {
		new Schedule().init();
	}
}
