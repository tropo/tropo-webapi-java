package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.voxeo.tropo.actions.AnswerAction;
import com.voxeo.tropo.actions.CallAction;
import com.voxeo.tropo.enums.Channel;
import com.voxeo.tropo.enums.Network;
import com.voxeo.tropo.enums.Voice;

public class AnswerActionTest {

	@Test
	public void testAnswer() {

		Tropo tropo = new Tropo();
		tropo.answer(Key.NAME("answer"), Key.TIMEOUT(10.0f), Key.LABEL("answerLabel"));
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"answer\":{\"name\":\"answer\",\"timeout\":10.0,\"label\":\"answerLabel\"}}]}");
	}

	@Test
	public void testCallFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.answer(Key.BARGEIN(true), Key.NAME("answer"), Key.TIMEOUT(10.0f), Key.LABEL("answerLabel"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'bargein' for action");
		}
	}	
		
	@Test
	public void testAllowSignals() {
		
		Tropo tropo = new Tropo();
		tropo.answer(Key.NAME("answerWithSignals"), Key.TIMEOUT(10.0f), Key.LABEL("answerLabel"), Key.ALLOW_SIGNALS("exit","stopHold"));
		assertEquals(tropo.text(),"{\"tropo\":[{\"answer\":{\"name\":\"answerWithSignals\",\"timeout\":10.0,\"label\":\"answerLabel\",\"allowSignals\":[\"exit\",\"stopHold\"]}}]}");
		
	}


	@Test
	public void testAnswerTraditional() {

		Tropo tropo = new Tropo();
		tropo.answer();				
		assertEquals(tropo.text(),"{\"tropo\":[{\"answer\":null}]}");
	}


	@Test
	public void testAnswerWithHeadersTraditional() {

		Tropo tropo = new Tropo();
		AnswerAction answer = tropo.answer(Key.NAME("name"));
		answer.headers(new String[]{"fooKey","fooValue"}, new String[]{"barKey","barValue"});
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"answer\":{\"name\":\"name\",\"headers\":{\"fooKey\":\"fooValue\",\"barKey\":\"barValue\"}}}]}");
	}

	
	@Test
	public void testAnswerCallWithHeadersTraditional() {

    Tropo tropo = new Tropo();
    Map<String, String> map = new LinkedHashMap<String, String>();
    map.put("fooKey", "fooValue");
    map.put("barKey", "barValue");
    tropo.answer(Key.NAME("name"), Key.HEADERS(map));
    assertEquals(tropo.text(),"{\"tropo\":[{\"answer\":{\"name\":\"name\",\"headers\":{\"fooKey\":\"fooValue\",\"barKey\":\"barValue\"}}}]}");
	}
	
	
	
	@Test
	public void testAnswerWithAllParameters() {

		Tropo tropo = new Tropo();
		AnswerAction answer = tropo.answer(Key.NAME("answerWithAll"), Key.TIMEOUT(11.1f), Key.LABEL("answerLabel"), Key.ALLOW_SIGNALS("special","stopHold"));
		answer.headers(new String[]{"fooKey","fooValue"}, new String[]{"barKey","barValue"});
		assertEquals(tropo.text(),"{\"tropo\":[{\"answer\":{\"name\":\"answerWithAll\",\"timeout\":11.1,\"label\":\"answerLabel\",\"allowSignals\":[\"special\",\"stopHold\"],\"headers\":{\"fooKey\":\"fooValue\",\"barKey\":\"barValue\"}}}]}");
		
	}
	
	
	@Test
	public void testQATest() {

		Tropo tropo = new Tropo();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Session-ID", "123;remote=0000");
		map.put("diversion", "sip:123@abc.com;reason=no-answer;privacy=off;screen=yes");
		tropo.answer(Key.HEADERS(map));
		tropo.say(Key.NAME("say"), Key.VALUE("Hello, you were the first to answer."));
		assertEquals(tropo.text(),"{\"tropo\":[{\"answer\":{\"headers\":{\"Session-ID\":\"123;remote=0000\",\"diversion\":\"sip:123@abc.com;reason=no-answer;privacy=off;screen=yes\"}}},{\"say\":[{\"name\":\"say\",\"value\":\"Hello, you were the first to answer.\"}]}]}");
	}
 

}
