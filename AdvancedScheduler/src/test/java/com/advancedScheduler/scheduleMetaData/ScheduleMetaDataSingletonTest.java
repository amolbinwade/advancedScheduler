package com.advancedScheduler.scheduleMetaData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.advancedScheduler.springConfig.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class,loader=AnnotationConfigContextLoader.class)
public class ScheduleMetaDataSingletonTest {
	
	@Autowired
	ScheduleMetaDataSingleton scheduleMetaData;
	
	@Test
	public void testScheduleMetaDataInstance(){
		scheduleMetaData.getTitle();		
	}

}
