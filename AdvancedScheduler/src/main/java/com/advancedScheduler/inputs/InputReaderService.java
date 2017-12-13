package com.advancedScheduler.inputs;

import java.util.Map;


import com.advancedscheduler.exceptions.ApplicationException;
import org.springframework.stereotype.*;

@Component
public interface InputReaderService {
	
	public Map<String,Integer> readSessionListFromFilePath(String filePath) throws ApplicationException;

	public Map<String, String> readScheduleInputDataFromFilePath(String filePath) throws ApplicationException;

}
