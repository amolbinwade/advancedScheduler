package com.advancedScheduler.constants;

public enum ScheduleInputEnum {
	
	TITLE("Title"),
	NUMBER_OF_DAYS("Days"),
	TRACKS_PER_DAY("Tracks"),
	MORNING_START_TIME("Start Time"),
	MORNING_END_TIME("Lunch Time"),
	NOON_START_TIME("Noon Start Time"),
	NOON_END_TIME("Noon End Time");
	
	
private final String value;
	
	private ScheduleInputEnum(String value){
		this.value = value;
	}
	
	public String value(){
		return this.value;
	}

}
