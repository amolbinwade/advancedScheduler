package com.advancedScheduler.inputs;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.advancedscheduler.exceptions.ApplicationException;

@Component("inputReaderService")
public class InputReaderServiceImpl implements InputReaderService{
	
	public InputReaderServiceImpl() {
	}

	public Map<String, Integer> readSessionListFromFilePath(String filePath) throws ApplicationException {
		Assert.isTrue(!StringUtils.isEmpty(filePath),"filePath cannot be empty");				
		Path path = FileSystems.getDefault().getPath(filePath);	
		Map<String,Integer> inputMap = null;
		try {			
			try(Stream<String> lines = Files.lines(path)){ 
				//trying to introduce Stream.parallel(), but still no multithreading observed
				inputMap = lines.parallel().collect(Collectors
						.toMap(line -> getKeyFromLine(line), line -> Integer.parseInt(getValueFromLine(line))));

				
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException("Error opening file: "+filePath);
		}

		return inputMap;
	}
	
	@Override
	public Map<String, String> readScheduleInputDataFromFilePath(String filePath) throws ApplicationException {
		Assert.isTrue(!StringUtils.isEmpty(filePath), "file path cannot be empty");
		Path path = FileSystems.getDefault().getPath(filePath);	
		Map<String,String> inputMap = null;
		try {			
			try(Stream<String> lines = Files.lines(path)){
				inputMap = lines.collect(Collectors
						.toMap(line -> getKeyFromLine(line), line -> getValueFromLine(line),
								(a,b) -> a));				
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException("Error opening file: "+filePath);
		}
		return inputMap;			
	}
	
	private String getKeyFromLine(String line){
		String[] keyValue = StringUtils.split(line, "=");
		return StringUtils.trimWhitespace(keyValue[0]);		
	}
	
	private String getValueFromLine(String line){
		String[] keyValue = StringUtils.split(line, "=");
		return StringUtils.trimWhitespace(keyValue[1]);		
	}
	


	
	
	

}
