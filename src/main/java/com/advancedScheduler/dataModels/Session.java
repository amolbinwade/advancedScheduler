package com.advancedScheduler.dataModels;

import org.joda.time.LocalTime;

@SuppressWarnings("rawtypes")
public abstract class Session implements Comparable{	

	private String title;
	
	public Session(){
		
	}
	
	public Session(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return this.title;
	}

	public abstract LocalTime getStartTime();

	public abstract LocalTime getEndTime();
	
	public abstract int getDuration();

}
