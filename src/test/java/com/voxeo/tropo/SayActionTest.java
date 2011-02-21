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
		tropo.say("1234");
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"1234\"}]}]}");
	}
	
	@Test
	public void testSayWithKeyArgument() {
		
		Tropo tropo = new Tropo();
		tropo.say(VALUE("1234"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"1234\"}]}]}");
	}
	
	@Test
	public void testSayWithArrayArgument() {
		
		Tropo tropo = new Tropo();
		tropo.say(VALUE("1234")).say(VALUE("abcd"),EVENT("nomatch:1"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"1234\"},{\"value\":\"abcd\",\"event\":\"nomatch:1\"}]}]}");
	}
	
	@Test
	public void testSayWithMoreThanTwoArrayArguments() {
		
		Tropo tropo = new Tropo();
		tropo.say(VALUE("1234")).say(VALUE("abcd"),EVENT("nomatch:1")).say(VALUE("zywx"),EVENT("nomatch:2"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"1234\"},{\"value\":\"abcd\",\"event\":\"nomatch:1\"},{\"value\":\"zywx\",\"event\":\"nomatch:2\"}]}]}");
	}

	@Test
	public void testSayAndOn() {
		
		Tropo tropo = new Tropo();
		tropo.say(VALUE("blah"));
		tropo.on(EVENT("error"),NEXT("error.json"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\"}]},{\"on\":{\"event\":\"error\",\"next\":\"error.json\"}}]}");
	}	
	
	@Test
	public void testFailsSayWithNoValueParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.say(VOICE(Voice.ALLISON));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'value'");
		}
	}
	
	@Test
	public void testSayAndOnWithAndSyntax() {
		
		Tropo tropo = new Tropo();
		tropo.and(
			Do.say(VALUE("blah")),
			Do.on(EVENT("error"),NEXT("error.json")));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\"}]},{\"on\":[{\"event\":\"error\",\"next\":\"error.json\"}]}]}");
	}

	@Test
	public void testTwoSaysWithArrays() {
		
		Tropo tropo = new Tropo();
		tropo.and(
			Do.say(VALUE("1234")).say(VALUE("abcd"),EVENT("nomatch:1")),
			Do.say(VALUE("0987")).say(VALUE("zyxw"),EVENT("nomatch:2"))
		);
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"1234\"},{\"value\":\"abcd\",\"event\":\"nomatch:1\"}]},{\"say\":[{\"value\":\"0987\"},{\"value\":\"zyxw\",\"event\":\"nomatch:2\"}]}]}");
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
			tropo.say(TO("test"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'to' for action");
		}
	}
	
	@Test
	public void testAllowUnnamedSignals() {
		
		Tropo tropo = new Tropo();
		tropo.say(VALUE("blah"),ALLOW_SIGNALS());
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"allowSignals\":\"\"}]}]}");
	}
	
	@Test
	public void testAllowSignals() {
		
		Tropo tropo = new Tropo();
		tropo.say(VALUE("blah"),ALLOW_SIGNALS("exit","stopHold"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"allowSignals\":[\"exit\",\"stopHold\"]}]}]}");
	}
	
	@Test
	public void testVoicesInLowercases() {
		
		Tropo tropo = new Tropo();
		tropo.say(VALUE("blah"), VOICE(Voice.ALLISON));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"voice\":\"allison\"}]}]}");
	}
	
	@Test
	public void testAsParameter() {
		
		Tropo tropo = new Tropo();
		tropo.say(VALUE("blah"), AS(As.DATE));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"blah\",\"as\":\"DATE\"}]}]}");
	}
}
