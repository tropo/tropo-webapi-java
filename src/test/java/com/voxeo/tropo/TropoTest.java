package com.voxeo.tropo;

import static com.voxeo.tropo.Key.*;
import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

import com.voxeo.tropo.actions.Do;
import com.voxeo.tropo.mock.MockHttpServletRequest;
import com.voxeo.tropo.mock.MockHttpServletResponse;

public class TropoTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testReject() {
		
		Tropo tropo = new Tropo();
		tropo.reject();
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"reject\":null}]}");
	}
	
	@Test
	public void testTropoRoot() {
		
		Tropo tropo = new Tropo();
		
		assertEquals(tropo.text(), "{\"tropo\":[]}");
	}	

	@Test
	public void testStopRecording() {
		
		Tropo tropo = new Tropo();
		tropo.stopRecording();
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"stopRecording\":null}]}");
	}
	
	@Test
	public void testParseJsonSession() {

		String json_session = "{\"session\":{\"id\":\"dih06n\",\"accountId\":\"33932\",\"timestamp\":\"2010-01-19T23:18:48.562Z\",\"userType\":\"HUMAN\",\"to\":{\"id\":\"tropomessaging@bot.im\",\"name\":\"unknown\",\"channel\":\"TEXT\",\"network\":\"JABBER\"},\"from\":{\"id\":\"john_doe@gmail.com\",\"name\":\"unknown\",\"channel\":\"TEXT\",\"network\":\"JABBER\"}}}";
		Tropo tropo = new Tropo();
		TropoSession session = tropo.session(json_session);
		assertNotNull(session);
		assertEquals(session.getTimestamp(),"2010-01-19T23:18:48.562Z");
		assertEquals(session.getTo().getName(),"unknown");
		assertEquals(session.getFrom().getId(),"john_doe@gmail.com");
	}		
	
	@Test
	public void testParseJsonSessionWithParameters() {

		String json_session = "{\"session\":{\"id\": \"76b05a0b25127dbf59a4627f6dcd38a7\", \"accountId\": \"12345\", \"timestamp\": \"2010-05-05T01:59:19.402Z\", \"userType\": \"NONE\", \"initialText\": null, \"callId\":\"092f931c4dddf0124ef426c56d26f98c\", \"parameters\": { \"token\": \"token_id\", \"action\": \"create\", \"myNum\":\"4075551212\"}}}";
		Tropo tropo = new Tropo();
		TropoSession session = tropo.session(json_session);
		assertNotNull(session);
		assertEquals(session.getAccountId(), "12345");
		assertEquals(session.getUserType(), "NONE");
		assertNotNull(session.getParameters());
		assertEquals(session.getParameters().get("token"),"token_id");
	}
	
	@Test
	public void testParseJsonSessionFromServletRequest() {

		String json_session = "{\"session\":{\"id\": \"76b05a0b25127dbf59a4627f6dcd38a7\", \"accountId\": \"12345\", \"timestamp\": \"2010-05-05T01:59:19.402Z\", \"userType\": \"NONE\", \"initialText\": null, \"callId\":\"092f931c4dddf0124ef426c56d26f98c\", \"parameters\": { \"token\": \"token_id\", \"action\": \"create\", \"myNum\":\"4075551212\"}}}";
		HttpServletRequest mockRequest = new MockHttpServletRequest(json_session);
		
		Tropo tropo = new Tropo();
		TropoSession session = tropo.session(mockRequest);
		assertNotNull(session);
		assertNotNull(session.getParameters());
		assertEquals(session.getParameters().get("token"),"token_id");
	}
	
	@Test
	public void testSayOnAndRecordTraditionalWay() {
		
		Tropo tropo = new Tropo();
		tropo.say("Welcome to the app");
		tropo.on(EVENT("hangup"), NEXT("/hangup.json"));
		tropo.record(NAME("foo"), BEEP(true), SEND_TONES(false), EXIT_TONE("#"), URL("http://sendme.com/tropo")).and(
			Do.say("Please say your account number"),
			Do.choices(VALUE("[5 DIGITS]"))
		);
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"Welcome to the app\"}]},{\"on\":{\"event\":\"hangup\",\"next\":\"/hangup.json\"}},{\"record\":{\"name\":\"foo\",\"beep\":true,\"send_tones\":false,\"exit_tone\":\"#\",\"url\":\"http://sendme.com/tropo\",\"say\":[{\"value\":\"Please say your account number\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
	}		

	@Test
	public void testReset() {
		
		Tropo tropo = new Tropo();		
		tropo.ask(NAME("foo"), BARGEIN(true), TIMEOUT(30.0f), REQUIRED(true));
		assertEquals(tropo.text(), "{\"tropo\":[{\"ask\":{\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"required\":true}}]}");
		
		tropo.reset();
		assertEquals(tropo.text(), "{\"tropo\":[]}");
	}		
	
	@Test
	public void testTropoResultSingle() {

		String json_session = "{\"result\":{\"sessionId\":\"CCFD9C86-1DD1-11B2-B76D-B9B253E4B7FB@161.253.55.20\",\"state\":\"ANSWERED\",\"sessionDuration\":2,\"sequence\":1,\"complete\":true,\"error\":null,\"actions\":{\"name\":\"zip\",\"attempts\":1,\"disposition\":\"SUCCESS\",\"confidence\":100,\"interpretation\":\"12345\",\"utterance\":\"1 2 3 4 5\"}}}";
		Tropo tropo = new Tropo();
		TropoResult result = tropo.parse(json_session);
		assertNotNull(result);
		assertEquals(result.getSequence(), 1);
		assertNotNull(result.getActions());
		assertEquals(result.getActions().get(0).getName(),"zip");
		assertEquals(result.getActions().get(0).getInterpretation(),"12345");
		assertEquals(result.getActions().get(0).getUtterance(),"1 2 3 4 5");
		assertEquals(result.getActions().get(0).getDisposition(),"SUCCESS");
	}		
	
	@Test
	public void testTropoResultArray() {

		String json_session = "{\"result\":{\"sessionId\":\"CCFD9C86-1DD1-11B2-B76D-B9B253E4B7FB@161.253.55.20\",\"state\":\"ANSWERED\",\"sessionDuration\":2,\"sequence\":1,\"complete\":true,\"error\":null,\"actions\":[{ \"name\": \"account_number\", \"attempts\": 1, \"disposition\": \"SUCCESS\", \"confidence\": 100, \"interpretation\": \"12345\", \"utterance\": \"1 2 3 4 5\", \"concept\": \"12345\" }, { \"name\": \"pin\", \"attempts\": 1, \"disposition\": \"SUCCESS\", \"confidence\": 100, \"interpretation\": \"9876\", \"utterance\": \"9 8 7 6\", \"concept\": \"9876\" }]}}";
		Tropo tropo = new Tropo();
		TropoResult result = tropo.parse(json_session);
		assertNotNull(result);
		assertNotNull(result.getActions());
		assertEquals(result.getActions().get(0).getName(),"account_number");
		assertEquals(result.getActions().get(1).getName(),"pin");
	}		
	
	@Test
	public void testObjectArrivesInJson() {

		String json_session = "{\"result\":{\"sessionId\":\"CCFD9C86-1DD1-11B2-B76D-B9B253E4B7FB@161.253.55.20\",\"state\":\"ANSWERED\",\"sessionDuration\":2,\"sequence\":1,\"complete\":true,\"error\":null,\"actions\":{\"name\":\"zip\",\"attempts\":1,\"disposition\":\"SUCCESS\",\"confidence\":100,\"interpretation\":\"12345\",\"utterance\":\"1 2 3 4 5\"}}}";
		Tropo tropo = new Tropo();
		TropoResult result = tropo.parse(json_session);
		assertEquals(result.getState(),"ANSWERED");
	}
	
	@Test
	public void testParseTropoResultFromServletRequest() {

		String json_session = "{\"result\":{\"sessionId\":\"CCFD9C86-1DD1-11B2-B76D-B9B253E4B7FB@161.253.55.20\",\"state\":\"ANSWERED\",\"sessionDuration\":2,\"sequence\":1,\"complete\":true,\"error\":null,\"actions\":[{ \"name\": \"account_number\", \"attempts\": 1, \"disposition\": \"SUCCESS\", \"confidence\": 100, \"interpretation\": \"12345\", \"utterance\": \"1 2 3 4 5\", \"concept\": \"12345\" }, { \"name\": \"pin\", \"attempts\": 1, \"disposition\": \"SUCCESS\", \"confidence\": 100, \"interpretation\": \"9876\", \"utterance\": \"9 8 7 6\", \"concept\": \"9876\" }]}}";
		HttpServletRequest request = new MockHttpServletRequest(json_session);
		
		Tropo tropo = new Tropo();
		TropoResult result = tropo.parse(request);
		assertNotNull(result);
		assertNotNull(result.getActions());
		assertEquals(result.getActions().get(0).getName(),"account_number");
		assertEquals(result.getActions().get(1).getName(),"pin");
	}		
	
	@Test
	public void testOutputIsRenderedToServletResponse() {

		Tropo tropo = new Tropo();
		tropo.say("1234");

		MockHttpServletResponse response = new MockHttpServletResponse();
		tropo.render(response);
		
		assertEquals(response.getContent(), "{\"tropo\":[{\"say\":[{\"value\":\"1234\"}]}]}");
	}	
	
	@Test
	public void testLaunchSession() {

		// This tropo test is hosted in a special Tropo username "hudson"'s account
		String token = "bb308b34ed83d54cab226f4af7969e4c7d7d9196cdb3210b5ef0cb345616629005bfd05efe3f4409cd496ca2";
		Tropo tropo = new Tropo();
		TropoLaunchResult result = tropo.launchSession(token);

		assertNotNull(result);
		assertEquals(result.isSuccess(),true);
		assertEquals(result.getToken(),token);
	}
	
	
	@Test
	public void testEmpty() {

		Tropo tropo = new Tropo();
		assertTrue(tropo.isEmpty());
		tropo.say("1234");
		assertFalse(tropo.isEmpty());
	}
}
