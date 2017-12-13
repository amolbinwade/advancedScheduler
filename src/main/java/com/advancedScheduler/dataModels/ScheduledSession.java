package com.advancedScheduler.dataModels;

import org.joda.time.LocalTime;
import org.springframework.util.Assert;

import com.advancedscheduler.exceptions.ApplicationException;

/**
 * @author amol
 * This class holds information of each session: topic, start time and end time.
 * This is added to the Track to create the complete track schedule.
 */
public class ScheduledSession extends Session {

	private LocalTime startTime;
	private LocalTime endTime;	
	
	
	public ScheduledSession(String title, LocalTime startTime, LocalTime endTime){
		super(title);
		this.startTime = startTime;
		this.endTime = endTime;
	}
	

	public LocalTime getStartTime() {
		return this.startTime;
	}

	public LocalTime getEndTime() {
		return this.endTime;
	}

	@Override
	public int getDuration() {
		return ((endTime.getMillisOfDay()-startTime.getMillisOfDay())/(60*1000));
	}


	@Override
	public int compareTo(Object o) {
		Assert.isTrue((o instanceof ScheduledSession), "Mis-matchingg types in comparable");
		ScheduledSession sch = (ScheduledSession) o;
		if (this.startTime.isBefore(sch.getStartTime())) {
			return -1;
		} else if (this.startTime.isEqual(sch.getStartTime())) {
			return 0;
		}
		return 1;
	}
	
}
