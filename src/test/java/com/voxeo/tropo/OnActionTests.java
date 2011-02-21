package com.voxeo.tropo;

import static com.voxeo.tropo.Key.EVENT;
import static com.voxeo.tropo.Key.NEXT;
import static com.voxeo.tropo.Key.TO;
import static com.voxeo.tropo.Key.VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.actions.Do;

public class OnActionTests {

	@Test
	public void testOn() {
		
		Tropo tropo = new Tropo();
		tropo.on(EVENT("hangup"),NEXT("myresource"));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"on\":{\"event\":\"hangup\",\"next\":\"myresource\"}}]}");
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
}
