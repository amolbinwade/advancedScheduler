package com.advancedScheduler.dataModels;

import org.joda.time.LocalTime;

public class UnscheduledSession extends Session {

	private int duration;
	
	public UnscheduledSession(String title, int duration) {
		super(title);
		this.duration = duration;
	}
	
	@Override
	public LocalTime getStartTime() {
		return null;
	}

	@Override
	public LocalTime getEndTime() {
		return null;
	}

	@Override
	public int getDuration() {
		return this.duration;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
