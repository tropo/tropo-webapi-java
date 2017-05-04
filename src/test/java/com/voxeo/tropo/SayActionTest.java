package com.voxeo.tropo;

import static com.voxeo.tropo.Key.*;
import static org.junit.Assert.*;

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
		tropo.say("1234", "say");
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"1234\",\"name\":\"say\"}]}]}");
	}
	
	@Test
	public void testSayWithKeyArgument() {
		
		Tropo tropo = new Tropo();
		tropo.say(Key.VALUE("1234"), Key.NAME("say"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"1234\",\"name\":\"say\"}]}]}");
	}
	
	@Test
	public void testSayAndOn() {
		
		Tropo tropo = new Tropo();
		tropo.say(Key.VALUE("blah"), Key.NAME("say"));
		tropo.on(Key.EVENT("error"),Key.NEXT("error.json"),Key.SAY_OF_ON("say of on"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"name\":\"say\"}]},{\"on\":{\"event\":\"error\",\"next\":\"error.json\",\"say\":[{\"value\":\"say of on\"}]}}]}");
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

	@Test
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
			Do.say(Key.VALUE("blah"), Key.NAME("say")),
			Do.on(Key.EVENT("error"),Key.NEXT("error.json")));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"name\":\"say\"}]},{\"on\":[{\"event\":\"error\",\"next\":\"error.json\"}]}]}");
	}

	@Test
	public void testSeveralSayCallsTraditionalWay() {
		
		Tropo tropo = new Tropo();
		
		tropo.say("foo", "say");
		tropo.say("bar", "say");
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"foo\",\"name\":\"say\"}]},{\"say\":[{\"value\":\"bar\",\"name\":\"say\"}]}]}");
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
		tropo.say(Key.VALUE("blah"),Key.NAME("say"),Key.ALLOW_SIGNALS());
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"name\":\"say\",\"allowSignals\":\"\"}]}]}");
	}
	
	@Test
	public void testAllowSignals() {
		
		Tropo tropo = new Tropo();
		tropo.say(Key.VALUE("blah"),Key.NAME("say"),Key.ALLOW_SIGNALS("exit","stopHold"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"name\":\"say\",\"allowSignals\":[\"exit\",\"stopHold\"]}]}]}");
	}
	
	@Test
	public void testVoicesInLowercases() {
		
		Tropo tropo = new Tropo();
		tropo.say(Key.VALUE("blah"), Key.NAME("say"), Key.VOICE(Voice.ALLISON));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"name\":\"say\",\"voice\":\"allison\"}]}]}");
	}
	
	@Test
	public void testAsParameter() {
		
		Tropo tropo = new Tropo();
		tropo.say(Key.VALUE("blah"), Key.NAME("say"), Key.AS(As.DATE));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"name\":\"say\",\"as\":\"DATE\"}]}]}");
	}
}
