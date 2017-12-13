package com.advancedScheduler.dataModels;

import java.util.List;

import com.advancedscheduler.exceptions.ApplicationException;

public interface Track {
	
	public void addSession(Session session) throws ApplicationException;
	
	public boolean isTrackFull();
	
	public int getNextAvailableBlockSize();
	
	public List<Session> getSessionList();
	

}
