package com.voxeo.tropo;

import static com.voxeo.tropo.Key.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.actions.Do;

public class OnActionTest {

	@Test
	public void testOn() {
		
		Tropo tropo = new Tropo();
		tropo.on(EVENT("hangup"),NEXT("myresource"));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"on\":{\"event\":\"hangup\",\"next\":\"myresource\"}}]}");
	}	

	@Test
	public void testOnWithValue() {
		
		Tropo tropo = new Tropo();
		tropo.on(EVENT("hangup"),VALUE("Sorry. We are hanging up."));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"on\":{\"event\":\"hangup\",\"value\":\"Sorry. We are hanging up.\"}}]}");
	}	
	
	@Test
	public void testFailsOnWithNoEventParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.on(NEXT("myresource"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'event'");
		}
	}
	
	@Test
	public void testOnFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.on(TO("hangup"),NEXT("myresource"));
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'to' for action");
		}
	}
	
	@Test
	public void testOnTraditionalWay() {
		
		Tropo tropo = new Tropo();
		tropo.on("hangup","myresource");
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"on\":{\"event\":\"hangup\",\"next\":\"myresource\"}}]}");
	}
	
	@Test
	public void testOnAndSay() {
		
		Tropo tropo = new Tropo();
		tropo.and(
			Do.on(EVENT("error"),NEXT("error.json")),
			Do.say(VALUE("blah")));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"on\":[{\"event\":\"error\",\"next\":\"error.json\"}]},{\"say\":[{\"value\":\"blah\"}]}]}");
	}
	
	
	@Test
	public void testOnNestedSay() {
		
		Tropo tropo = new Tropo();
		tropo.ask(NAME("foo"),BARGEIN(true),TIMEOUT(30.0f),REQUIRED(true)).and(
	            Do.say("Log in as voice, text, or log out"),
	            Do.on(EVENT("success"),NEXT("/tropoResultIn")).say("Oh Kay")
	    );
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"ask\":{\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"required\":true,\"say\":[{\"value\":\"Log in as voice, text, or log out\"}],\"on\":[{\"event\":\"success\",\"next\":\"/tropoResultIn\",\"say\":[{\"value\":\"Oh Kay\"}]}]}}]}");
	}
}
