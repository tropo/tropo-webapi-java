package com.tropo.quartz;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.voxeo.tropo.Tropo;

public class LongJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		// Not that long
		System.out.println("Sleeping 2 seconds");
		try {
			Thread.sleep(2000);

			// This coude launches your Tropo application. You can configure your tropo application to 
			// do things like sending an SMS, calliing a phone, etc. 
			String token = "f46f1f14bdd7684d9195ad83e1bbce021d0f024ad5e56e8c99cbd10e9cf3b2b026cb68749b41cb487dd09a5d";
			Tropo tropo = new Tropo();
			Map<String, String> params = new HashMap<String, String>();
			params.put("message","This is an SMS message");
			params.put("numberToDial", "+34637710708");
			tropo.launchSession(token, params);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
}
