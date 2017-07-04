package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class OnActionTest {

	@Test
	public void testOn() {
		
		Tropo tropo = new Tropo();
		tropo.on(Key.EVENT("connect"),Key.NEXT("myresource"),Key.SAY_OF_ON("Nice answer!"),Key.POST("http://example.com/tropo"));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"on\":{\"event\":\"connect\",\"next\":\"myresource\",\"say\":[{\"value\":\"Nice answer!\"}],\"post\":\"http://example.com/tropo\"}}]}");
	}	

	@Test
	public void testFailsOnWithNoEventParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.on(Key.NEXT("myresource"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'event'");
		}
	}

	@Test
  public void testFailsOnWithNoSayvalue() {

    Tropo tropo = new Tropo();
    try {
      tropo.on(Key.EVENT("hangup"),Key.NEXT("myresource"),Key.SAY_OF_ON(new com.voxeo.tropo.actions.OnAction.Say(null)));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(), "Missing required property: value of on.say");
    }
  }

	@Test
  public void testFailsOnWithNoSayvalue1() {

    Tropo tropo = new Tropo();
    try {
      tropo.on(Key.EVENT("hangup"),Key.NEXT("myresource"),Key.SAY_OF_ON(new com.voxeo.tropo.actions.OnAction.Say("")));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(), "Missing required property: value of on.say");
    }
  }
	
	@Test
	public void testOnFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.on(Key.TO("hangup"),Key.NEXT("myresource"));
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'to' for action");
		}
	}
}
