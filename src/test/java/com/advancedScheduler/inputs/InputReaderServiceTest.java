package com.advancedScheduler.inputs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.advancedScheduler.springConfig.AppConfig;
import com.advancedscheduler.exceptions.ApplicationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class,loader=AnnotationConfigContextLoader.class)
public class InputReaderServiceTest {
	
	@Autowired
	@Qualifier("inputReaderService")
	InputReaderService readerService;
	
	@Test(expected=IllegalArgumentException.class)
	public void testBlankPathExceptionReadSessionListFromFilePath() throws ApplicationException{
		String filePath = "";
		
		readerService.readSessionListFromFilePath(filePath);
	}
	
	@Test(expected=ApplicationException.class)
	public void testInvalidPathExceptionReadSessionListFromFilePath() throws ApplicationException{
		String filePath = "/home/amol/Programs/advancedScheduler/test2.txt";
		
		readerService.readSessionListFromFilePath(filePath);
	}
	
	@Test
	public void testReadSessionListFromFilePath() throws ApplicationException{
		String filePath = "/home/amol/Programs/advancedScheduler/test.txt";
		
		readerService.readSessionListFromFilePath(filePath);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testBlankPathExceptionReadScheduleInputFromFilePath() throws ApplicationException{
		String path = null;
		readerService.readScheduleInputDataFromFilePath(path);		
	}
	
	@Test(expected=ApplicationException.class)
	public void testInvalidPathExceptionReadScheduleInputFromFilePath() throws ApplicationException{
		String filePath = "/home/amol/Programs/advancedScheduler/test2.txt";
		
		readerService.readScheduleInputDataFromFilePath(filePath);
	}
	
	@Test
	public void testReadScheduleInputFromFilePath() throws ApplicationException{
		String filePath = "/home/amol/Programs/advancedScheduler/ScheduleInput.txt";
		
		readerService.readScheduleInputDataFromFilePath(filePath);
	}
	
	

}
