package com.advancedScheduler.util;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

public class Utility {
	
	private static org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("hh:mm a");
	
	public static String getFormattedDateString(LocalTime time){
		return formatter.print(time);
	}

}
