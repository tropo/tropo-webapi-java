package com.tropo.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class TropoQuartzExample {

	public static void main(String[] args) throws Exception {

		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			
	        // define the job and tie it to our HelloJob class
	        JobDetail job = newJob(LongJob.class)
	            .withIdentity("job1", "group1")
	            .build();
	        
	        // Trigger the job to run now, and then repeat every 40 seconds
	        Trigger trigger = newTrigger()
	            .withIdentity("trigger1", "group1")
	            .startNow()
	            .withSchedule(simpleSchedule()
	                    .withIntervalInSeconds(40000)
	                    .repeatForever())            
	            .build();
	        
	        // Tell quartz to schedule the job using our trigger
	        scheduler.scheduleJob(job, trigger);

	        // Wait a few seconds
	        Thread.sleep(30000);
	        
			scheduler.shutdown();			
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}
}
