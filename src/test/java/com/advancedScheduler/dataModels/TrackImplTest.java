package com.advancedScheduler.dataModels;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.Assert;

import com.advancedScheduler.scheduleMetaData.ScheduleMetaDataSingleton;
import com.advancedScheduler.springConfig.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class,loader=AnnotationConfigContextLoader.class)
public class TrackImplTest {

	@Autowired
	ScheduleMetaDataSingleton metadata;
	
	TrackImpl track;
	 
	@Before
	public void config(){
		this.track = new TrackImpl(1, this.metadata);
	}
	
	@Test
	public void testIsSlotAvailable() throws Exception{		
		Method m = null;
		try {
			m = this.track.getClass().getDeclaredMethod("isSlotAvailable", int.class);
			m.setAccessible(true);
			Assert.isTrue((Boolean) m.invoke(this.track, 90), "Failed");
			Assert.isTrue(!((Boolean)m.invoke(this.track, 500)), "Failed");
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof IllegalArgumentException){
				throw e;
			}
		}		
	}
	
	@Test
	public void testGetNextAvailableSlotSize() throws Exception{
		Method m = null;
		try {
			m = this.track.getClass().getDeclaredMethod("getNextAvailableBlockSize", null);
			m.setAccessible(true);
			org.junit.Assert.assertEquals(new Integer(180), m.invoke(this.track, null));
			
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
			e.printStackTrace();
			if(e instanceof IllegalArgumentException){
				throw e;
			}
		}
	}
	
	@Test
	public void testAddSession() throws Exception{		
		Method m = null;
		try {
			m = this.track.getClass().getDeclaredMethod("addSession", Session.class);
			m.setAccessible(true);
			Session session = new UnscheduledSession("Test", 90);
			m.invoke(this.track, session);
			org.junit.Assert.assertEquals(new Integer(90), (Integer)this.track.getNextAvailableBlockSize());
			
			System.out.println(this.track.toString());
			
			Session session1 = new UnscheduledSession("Test1", 30);
			m.invoke(this.track, session1);
			org.junit.Assert.assertEquals(new Integer(60), (Integer)this.track.getNextAvailableBlockSize());
			
			System.out.println(this.track.toString());
			
			Session session2 = new UnscheduledSession("Test2", 60);
			m.invoke(this.track, session2);
			org.junit.Assert.assertEquals(new Integer(240), (Integer)this.track.getNextAvailableBlockSize());
			
			System.out.println(this.track.toString());
			
			Session session3 = new UnscheduledSession("Test3", 90);
			m.invoke(this.track, session3);
			org.junit.Assert.assertEquals(new Integer(150), (Integer)this.track.getNextAvailableBlockSize());			
			System.out.println(this.track.toString());
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof IllegalArgumentException){
				throw e;
			}
			if(e instanceof InvocationTargetException){
				throw e;
			}
		}		
	}
}
