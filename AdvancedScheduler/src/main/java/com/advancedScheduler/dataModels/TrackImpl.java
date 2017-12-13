package com.advancedScheduler.dataModels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalTime;

import com.advancedScheduler.scheduleMetaData.ScheduleMetaDataSingleton;
import com.advancedscheduler.exceptions.ApplicationException;

import static com.advancedScheduler.util.Utility.*;

/**
 * @author amol
 * This class has the list of scheduled Session objects.
 * There can be multiple Track objects in a Day.
 */
public class TrackImpl implements Track { 
	
	private int trackId;
	private List<Session> sessionList;
	private ScheduleMetaDataSingleton metaData;
	private LocalTime currentPoint;
	
	public TrackImpl(){
		
	}
	
	public TrackImpl(int trackId, ScheduleMetaDataSingleton metaData){
		this.trackId = trackId;
		this.metaData = metaData;
		this.sessionList = new ArrayList<Session>();
		this.currentPoint = this.metaData.getMorningStartTime();
	}

	/* This method adds an UnscheduledSession to Track by scheduling it into ScheduledSession 
	 * 
	 */
	
	@Override
	public void addSession(Session session) throws ApplicationException {
		this.validateSessionBeforeAdding(session);
		//is currentPoint in morning slot and enough slot size available then add
		if(this.isMorningSlotActive()) {
			if (this.getNextAvailableBlockSize() >= session.getDuration()) {
				this.addSessionAndMoveCurrentPoint(session);
				return;
			} else { //if currentPoint in morning slot but enough slot size not available then forward to noon start time 
				this.forwardCurrentPointToNoonStartTime();
			}
		}		
		//If in between Morning end time and Noon start time then forward to Noon StartTime
		if (this.isCurrentPointInLunchTime()) {
			this.forwardCurrentPointToNoonStartTime();
		}
		
		//if currentPoint in noon slot and enough slot size available then add session
		if (!this.isMorningSlotActive()) {
			if (this.getNextAvailableBlockSize() >= session.getDuration()) {
				this.addSessionAndMoveCurrentPoint(session);
			} else { //if currentPoint in noon slot and enough slot size not available then throw error
				throw new ApplicationException("Slot not available to accomodate this session.");
			}
		}
		
			
		
	}
	
	private boolean isCurrentPointInLunchTime() {
		if ((this.currentPoint.isAfter(this.metaData.getMorningEndTime())
				|| this.currentPoint.isEqual(this.metaData.getMorningEndTime()))
				&& (this.currentPoint.isBefore(this.metaData.getNoonStartTime()))) {
			return true;
		}
		return false;
	}

	private void forwardCurrentPointToNoonStartTime() {
		this.currentPoint = this.metaData.getNoonStartTime().plusMinutes(0);		
	}

	private void addSessionAndMoveCurrentPoint(Session session) {
		
		//initialize start and end time of session and move currentPoint 
		LocalTime sessionStartTime = this.currentPoint.plusMinutes(0);
		LocalTime sessionEndTime = this.currentPoint.plusMinutes(session.getDuration());
		this.currentPoint = this.currentPoint.plusMinutes(session.getDuration());
		
		Session schSession = new ScheduledSession(session.getTitle(), sessionStartTime, sessionEndTime);
		this.sessionList.add(schSession);
		
	}

	private void validateSessionBeforeAdding(Session session) throws ApplicationException {
		if(session instanceof ScheduledSession){
			throw new ApplicationException("Incorrect Session type passed.");
		}
		
		if (!isSlotAvailable(session.getDuration())) {
			throw new ApplicationException("Slot not available to accomodate this session.");
		}
		
	}

	@Override
	public boolean isTrackFull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNextAvailableBlockSize() {
		if(isMorningSlotActive()){
			return (this.metaData.getMorningEndTime().getMillisOfDay()-this.currentPoint
					.getMillisOfDay())/this.getMillisOfHour();
		} 
		// if before noon start time then get size of complete Noon slot, else give NoonEndTime-currentPoint
		if (this.currentPoint.isBefore(this.metaData.getNoonStartTime())) {
			return (this.metaData.getNoonEndTime().getMillisOfDay()-this.metaData.getNoonStartTime()
					.getMillisOfDay())/this.getMillisOfHour();
		} else {
			return (this.metaData.getNoonEndTime().getMillisOfDay()-this.currentPoint
					.getMillisOfDay())/this.getMillisOfHour();
		}
	}

	private int getMillisOfHour() {		
		return 60*1000;
	}

	@Override
	public List<Session> getSessionList() {
		// TODO Auto-generated method stub
		return null;
	}
	
/*
 *  1.  if in morning slot
 *  	- if enough slot available return true
 *  	if enough slot available in noon session return true, else false 
 */
	private boolean isSlotAvailable(int duration){
		if (this.isMorningSlotActive()){
			int diff = (this.metaData.getMorningEndTime().getMillisOfDay()-this.currentPoint
					.getMillisOfDay())/(60*1000);
			if (diff >= duration){
				return true;
			} else {
				int noonSlot = (this.metaData.getNoonEndTime().getMillisOfDay()-this.metaData.getNoonStartTime()
						.getMillisOfDay())/(60*1000);
				if (noonSlot >= duration){
					return true;
				}
			}
		} else {
			int diff = (this.metaData.getNoonEndTime().getMillisOfDay()-this.currentPoint
					.getMillisOfDay())/(60*1000);
			if (diff >= duration){
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isMorningSlotActive(){
		return this.currentPoint.isBefore(this.metaData.getMorningEndTime());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString(){
		StringBuffer str = new StringBuffer();
		str.append("Track"+"\n");
		str.append("ID: "+this.trackId+"\n");
		Collections.sort(this.sessionList);
		int count = 1;
		for(Session s : this.sessionList) {
			str.append("-------------------------"+"\n");
			str.append("Session: " + count+"\n");
			str.append("Title: "+s.getTitle()+"\n");
			str.append("Duration: "+s.getDuration()+"\n");
			str.append("Start Time: " + getFormattedDateString(s.getStartTime())+"\n");
			str.append("End Time: " + getFormattedDateString(s.getEndTime())+"\n");		
			count++;
		}
		return str.toString();
		
	}
	

}
