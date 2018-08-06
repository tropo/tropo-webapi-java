package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.voxeo.tropo.actions.Do;
import com.voxeo.tropo.enums.As;
import com.voxeo.tropo.enums.Voice;

public class SayActionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSay() {
		
		Tropo tropo = new Tropo();
		tropo.say("1234");
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"1234\"}]}]}");
	}
	
	@Test
	public void testSayWithKeyArgument() {
		
		Tropo tropo = new Tropo();
		tropo.say(Key.VALUE("1234"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"1234\"}]}]}");
	}
	
	@Test
	public void testSayAndOn() {
		
		Tropo tropo = new Tropo();
		tropo.say(Key.VALUE("blah"));
		tropo.on(Key.EVENT("error"),Key.NEXT("error.json"),Key.SAY_OF_ON("say of on"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\"}]},{\"on\":{\"event\":\"error\",\"next\":\"error.json\",\"say\":[{\"value\":\"say of on\"}]}}]}");
	}	
	
	@Test
	public void testFailsSayWithNoValueParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.say(Key.VOICE(Voice.ALLISON));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'value'");
		}
	}

	@Deprecated
  public void testFailsSayWithNoNameParameter() {

    Tropo tropo = new Tropo();
    try {
      tropo.say(Key.VALUE("Hello"));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(), "Missing required property: 'name'");
    }
  }
	
	@Test
	public void testSayAndOnWithAndSyntax() {
		
		Tropo tropo = new Tropo();
		tropo.and(
			Do.say(Key.VALUE("blah")),
			Do.on(Key.EVENT("error"),Key.NEXT("error.json")));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\"}]},{\"on\":[{\"event\":\"error\",\"next\":\"error.json\"}]}]}");
	}

	@Test
	public void testSeveralSayCallsTraditionalWay() {
		
		Tropo tropo = new Tropo();
		
		tropo.say("foo");
		tropo.say("bar");
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"foo\"}]},{\"say\":[{\"value\":\"bar\"}]}]}");
	}		

	@Test
	public void testSayFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.say(Key.TO("test"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'to' for action");
		}
	}
	
	@Test
	public void testAllowUnnamedSignals() {
		
		Tropo tropo = new Tropo();
		tropo.say(Key.VALUE("blah"),Key.ALLOW_SIGNALS());
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"allowSignals\":\"\"}]}]}");
	}
	
	@Test
	public void testAllowSignals() {
		
		Tropo tropo = new Tropo();
		tropo.say(Key.VALUE("blah"),Key.ALLOW_SIGNALS("exit","stopHold"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"allowSignals\":[\"exit\",\"stopHold\"]}]}]}");
	}
	
	@Test
	public void testVoicesInLowercases() {
		
		Tropo tropo = new Tropo();
		tropo.say(Key.VALUE("blah"), Key.VOICE(Voice.ALLISON));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"voice\":\"allison\"}]}]}");
	}
	
	@Test
	public void testAsParameter() {
		
		Tropo tropo = new Tropo();
		tropo.say(Key.VALUE("blah"), Key.AS(As.DATE));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"as\":\"DATE\"}]}]}");
	}

	@Test
  public void testSayWithMedias() {
    
    Tropo tropo = new Tropo();
    tropo.say(Key.VALUE("This is the subject"), Key.MEDIA("http://server.com/1.jpg", "this is a inline text content", "http://filehosting.tropo.com/account/1/2.text"));
    
    assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"This is the subject\",\"media\":[\"http://server.com/1.jpg\",\"this is a inline text content\",\"http://filehosting.tropo.com/account/1/2.text\"]}]}]}");
  }
}
