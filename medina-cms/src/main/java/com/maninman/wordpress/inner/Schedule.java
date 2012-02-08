package com.maninman.wordpress.inner;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;


@Component
public class Schedule {

	long interval = 6L * 60 * 60 * 1000;
	
	long delay = 20L * 1000;
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
	TimerTask laoNanRenTask;
	
	@PostConstruct
	public void init(){
		Timer timer = new Timer();	
		timer.schedule( laoNanRenTask , delay, interval);
	}
}
