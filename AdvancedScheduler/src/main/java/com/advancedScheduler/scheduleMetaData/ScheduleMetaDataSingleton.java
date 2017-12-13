package com.advancedScheduler.scheduleMetaData;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import com.advancedScheduler.constants.ScheduleInputEnum;
import com.advancedScheduler.inputs.InputReaderService;
import com.advancedscheduler.exceptions.ApplicationException;

/*
 * @author amol
 *This class is a singleton representation of the Schedule input.
 *It will be passed to the algorithm for scheduling the sessions as input
 */
@Component("scheduleMetaData")
@Scope(BeanDefinition.SCOPE_SINGLETON) //by default Spring bean has singleton scope but declared explicitly to point it
public class ScheduleMetaDataSingleton {

	private static ScheduleMetaDataSingleton instance;
	private String title;
	private Integer numberOfDays;
	private Integer numberOfTracks;
	private LocalTime morningStartTime;
	private LocalTime morningEndTime;
	private LocalTime noonStartTime;
	private LocalTime noonEndTime;

	private static org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("hh:mm a");

	@Autowired
	@Qualifier("inputReaderService")
	private InputReaderService inputService;

	@Autowired
	private Environment env;


	public String getTitle() {
		return title;
	}

	public Integer getNumberOfDays() {
		return numberOfDays;
	}

	public Integer getNumberOfTracks() {
		return numberOfTracks;
	}

	public LocalTime getMorningStartTime() {
		return morningStartTime;
	}

	public LocalTime getMorningEndTime() {
		return morningEndTime;
	}

	public LocalTime getNoonStartTime() {
		return noonStartTime;
	}

	public LocalTime getNoonEndTime() {
		return noonEndTime;
	}

	private ScheduleMetaDataSingleton() {

	}

	private void setMetaData(String title, Integer numberOfDays,
			Integer numberOfTracks, LocalTime morningStartTime,
			LocalTime morningEndTime, LocalTime noonStartTime, 
			LocalTime noonEndTime){
		this.title = title;
		this.numberOfDays = numberOfDays;
		this.numberOfTracks = numberOfTracks;
		this.morningStartTime = morningStartTime;
		this.morningEndTime = morningEndTime;
		this.noonStartTime = noonStartTime;
		this.noonEndTime = noonEndTime;

	}

	@Bean("scheduleMetaData")
	public static ScheduleMetaDataSingleton getInstance() throws ApplicationException {		
		if (instance == null){
			synchronized (ScheduleMetaDataSingleton.class) {
				if(instance == null){
					instance = new ScheduleMetaDataSingleton();
				}
			}
		}
		return instance;
	}


	public void  afterPropertiesSet() throws ApplicationException {					

		String title = null;
		Integer numberOfDays = null;
		Integer numberOfTracks  = null;
		LocalTime morningStartTime  = null;
		LocalTime morningEndTime  = null;
		LocalTime noonStartTime  = null; 
		LocalTime noonEndTime  = null;

		try {
			Map<String, String> inputMap = inputService.readScheduleInputDataFromFilePath(env.getProperty("AdvancedScheduler.schedule_metadata_file"));

			for (Map.Entry<String, String> entry : inputMap.entrySet()) {

				if (ScheduleInputEnum.TITLE.value().equals(entry.getKey())) {
					title = entry.getValue();
				} else if (ScheduleInputEnum.NUMBER_OF_DAYS.value().equals(entry.getKey())) {
					numberOfDays = Integer.parseInt(entry.getValue());
				} else if (ScheduleInputEnum.TRACKS_PER_DAY.value().equals(entry.getKey())) {
					numberOfTracks = Integer.parseInt(entry.getValue());
				} else if (ScheduleInputEnum.MORNING_START_TIME.value().equals(entry.getKey())) {
					morningStartTime = formatter.parseLocalTime(entry.getValue());
				}  else if (ScheduleInputEnum.MORNING_END_TIME.value().equals(entry.getKey())) {
					morningEndTime = formatter.parseLocalTime(entry.getValue());
				}  else if (ScheduleInputEnum.NOON_START_TIME.value().equals(entry.getKey())) {
					noonStartTime = formatter.parseLocalTime(entry.getValue());
				}  else if (ScheduleInputEnum.NOON_END_TIME.value().equals(entry.getKey())) {
					noonEndTime = formatter.parseLocalTime(entry.getValue());
				} 
			}
			this.setMetaData(title, numberOfDays, numberOfTracks, morningStartTime, morningEndTime, noonStartTime, noonEndTime);
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw e;
		}

	}

}


