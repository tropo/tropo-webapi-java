package com.voxeo.tropo;

import static com.voxeo.tropo.Key.BARGEIN;
import static com.voxeo.tropo.Key.BEEP;
import static com.voxeo.tropo.Key.EVENT;
import static com.voxeo.tropo.Key.NAME;
import static com.voxeo.tropo.Key.NEXT;
import static com.voxeo.tropo.Key.REQUIRED;
import static com.voxeo.tropo.Key.TIMEOUT;
import static com.voxeo.tropo.Key.URL;
import static com.voxeo.tropo.Key.VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;

import com.voxeo.tropo.actions.AskAction.Say;
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
		tropo.say("Welcome to the app", "say");
		tropo.on(EVENT("hangup"), NEXT("/hangup.json"), Key.SAY_OF_ON("hangup"));
		tropo.record(NAME("foo"), BEEP(true), URL("http://sendme.com/tropo")).and(
			Do.say("Please say your account number","say"),
			Do.choices(VALUE("[5 DIGITS]"))
		);
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"Welcome to the app\",\"name\":\"say\"}]},{\"on\":{\"event\":\"hangup\",\"next\":\"/hangup.json\",\"say\":[{\"value\":\"hangup\"}]}},{\"record\":{\"name\":\"foo\",\"beep\":true,\"url\":\"http://sendme.com/tropo\",\"say\":[{\"value\":\"Please say your account number\",\"name\":\"say\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
	}		

	@Test
	public void testReset() {
		
		Tropo tropo = new Tropo();		
		tropo.ask(Key.CHOICES_OF_ASK("[5 DIGITS]"),Key.SAY_OF_ASK(new Say("Please say your account number")),NAME("foo"), BARGEIN(true), TIMEOUT(30.0f), REQUIRED(true));
		assertEquals(tropo.text(), "{\"tropo\":[{\"ask\":{\"choices\":{\"value\":\"[5 DIGITS]\"},\"say\":[{\"value\":\"Please say your account number\"}],\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"required\":true}}]}");
		
		tropo.reset();
		assertEquals(tropo.text(), "{\"tropo\":[]}");
	}		
	
	@Test
	public void testTropoResultSingle() {

		String json_session = "{\"result\":{\"sessionId\":\"CCFD9C86-1DD1-11B2-B76D-B9B253E4B7FB@161.253.55.20\",\"state\":\"ANSWERED\",\"sessionDuration\":2,\"sequence\":1,\"complete\":true,\"error\":null,\"actions\":{\"name\":\"zip\",\"attempts\":1,\"disposition\":\"SUCCESS\",\"confidence\":100,\"interpretation\":\"12345\",\"utterance\":\"1 2 3 4 5\"}}}";
		Tropo tropo = new Tropo();
		TropoResult result = tropo.parse(json_session);
		assertNotNull(result);
		assertEquals(result.getSequence(), new Integer(1));
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
		tropo.say("1234", "say");

		MockHttpServletResponse response = new MockHttpServletResponse();
		tropo.render(response);
		
		assertEquals(response.getContent(), "{\"tropo\":[{\"say\":[{\"value\":\"1234\",\"name\":\"say\"}]}]}");
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

	@Test(expected = TropoException.class)
	public void testLaunchSessionWithProvidedHttpClientExpectTimeout() {

		// This tropo test is hosted in a special Tropo username "hudson"'s account
		String token = "bb308b34ed83d54cab226f4af7969e4c7d7d9196cdb3210b5ef0cb345616629005bfd05efe3f4409cd496ca2";
		Tropo tropo = new Tropo();

		//creates a config with 1 ms socket timeout, will not connect in time
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1).build();
		HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();

		TropoLaunchResult result = tropo.launchSession(token, Collections.EMPTY_MAP, httpClient);
	}

	@Test
	public void testEmpty() {

		Tropo tropo = new Tropo();
		assertTrue(tropo.isEmpty());
		tropo.say("1234", "say");
		assertFalse(tropo.isEmpty());
	}

	@Test
	public void testSession() {

	  String requestBody = "{\"session\":{\"id\":\"0206f42b41db24008375ca4e745dc784\",\"accountId\":\"1\",\"applicationId\":\"1\",\"timestamp\":\"2017-04-27T08:55:49.252Z\",\"userType\":\"HUMAN\",\"initialText\":null,\"subject\": \"Inbound MMS subject\",\"initialMedia\":[{\"status\":\"success\",\"media\": \"http://filehosting.tropo.com/account/1.jpg\"},{\"status\":\"success\",\"text\": \"this is text\"},{\"status\":\"failure\",\"disposition\": \"Failed to upload: 500 Internal Error\",\"media\": \"2.jpg\"}],\"callId\":\"363241bb23fd2e2fd56775f112afb27f\",\"to\":{\"id\":\"9999452355\",\"e164Id\":\"9999452355\",\"name\":\"9999452355\",\"channel\":\"VOICE\",\"network\":\"SIP\"},\"from\":{\"id\":\"pengxli\",\"e164Id\":\"pengxli\",\"name\":\"pengxli\",\"channel\":\"VOICE\",\"network\":\"SIP\"},\"headers\":{\"Call-ID\":\"83369ZmNjOTdlZWVmOTk4ZjVkMmM2ODg3YWMyYTExZDRmMGU\",\"CSeq\":\"1 INVITE\",\"Max-Forwards\":\"69\",\"Request URI\":\"sip:9999452355@10.140.254.38;x-rt=0\",\"Record-Route\":\"<sip:192.168.26.102:5060;transport=udp;lr>\",\"x-sid\":\"0721d0422c85a7e0eb0c6dc4949827ea\",\"User-Agent\":\"X-Lite release 4.9.7.1 stamp 83369\",\"From\":\"<sip:pengxli@10.140.254.38>;tag=adf8ca71\",\"Supported\":\"replaces\",\"Allow\":\"SUBSCRIBE\r\nNOTIFY\r\nINVITE\r\nACK\r\nCANCEL\r\nBYE\r\nREFER\r\nINFO\r\nOPTIONS\r\nMESSAGE\",\"Via\":\"SIP/2.0/UDP 192.168.26.102:5060;branch=z9hG4bK1rc8iqaigvz77;rport=5060\r\nSIP/2.0/UDP 192.168.26.1:5678;branch=z9hG4bK-524287-1---33c9837c07a3ea75;rport=5678\",\"Contact\":\"<sip:pengxli@192.168.26.1:5678>\",\"To\":\"<sip:9999452355@10.140.254.38>\",\"Content-Length\":\"335\",\"Content-Type\":\"application/sdp\"}}}";
	  HttpServletRequest mockRequest = new MockHttpServletRequest(requestBody);

	  Tropo tropo = new Tropo();
	  TropoSession session = tropo.session(mockRequest);
	  assertNotNull(session);
	  assertEquals(session.getAccountId(), "1");
	  assertEquals(session.getCallId(), "363241bb23fd2e2fd56775f112afb27f");
	  assertEquals(session.getId(), "0206f42b41db24008375ca4e745dc784");
	  assertEquals(session.getInitialText(), null);
	  assertEquals(session.getTimestamp(), "2017-04-27T08:55:49.252Z");
	  assertEquals(session.getUserType(), "HUMAN");
	  assertEquals(session.getSubject(), "Inbound MMS subject");
	  List<InitialMedia> initialMedias = session.getInitialMedia();
	  assertEquals(initialMedias.get(0).getStatus(), "success");
	  assertEquals(initialMedias.get(0).getMedia(), "http://filehosting.tropo.com/account/1.jpg");
	  assertEquals(initialMedias.get(1).getStatus(), "success");
    assertEquals(initialMedias.get(1).getText(), "this is text");
    assertEquals(initialMedias.get(2).getStatus(), "failure");
    assertEquals(initialMedias.get(2).getDisposition(), "Failed to upload: 500 Internal Error");
    assertEquals(initialMedias.get(2).getMedia(), "2.jpg");

	  TropoEntity to = session.getTo();
	  assertNotNull(to);
	  assertEquals(to.getId(), "9999452355");
	  assertEquals(to.getE164Id(), "9999452355");
	  assertEquals(to.getName(), "9999452355");
	  assertEquals(to.getChannel(), "VOICE");
	  assertEquals(to.getNetwork(), "SIP");

	  TropoEntity from = session.getFrom();
	  assertNotNull(from);
    assertEquals(from.getId(), "pengxli");
    assertEquals(from.getE164Id(), "pengxli");
    assertEquals(from.getName(), "pengxli");
    assertEquals(from.getChannel(), "VOICE");
    assertEquals(from.getNetwork(), "SIP");

    assertEquals(session.getParameters(), null);

    assertNotNull(session.getHeaders());
	}

	@Test
	public void testResult() {

	  String requestBody = "{\"result\":{\"sessionId\":\"c377075800c4bad1031ba7eca97641c1\",\"callId\":\"15c1e1abe490752831f98ee6c6ea2b2f\",\"state\":\"ANSWERED\",\"sessionDuration\":10,\"sequence\":1,\"complete\":true,\"error\":null,\"calledid\":\"9999452355\",\"actions\":[{\"name\":\"one\",\"attempts\":1,\"disposition\":\"SUCCESS\",\"confidence\":100,\"interpretation\":\"1\",\"utterance\":\"1\",\"value\":\"1\"},{\"name\":\"two\",\"attempts\":1,\"disposition\":\"SUCCESS\",\"confidence\":100,\"interpretation\":\"12\",\"utterance\":\"12\",\"value\":\"12\"}]}}";
	  Tropo tropo = new Tropo();
	  TropoResult result = tropo.parse(requestBody);
	  assertNotNull(result);
	  assertEquals(result.getCallId(), "15c1e1abe490752831f98ee6c6ea2b2f");
	  assertEquals(result.getCalledId(), "9999452355");
	  assertEquals(result.isComplete(), true);
	  assertEquals(result.getError(), null);
	  assertEquals(result.getSequence(), new Integer(1));
	  assertEquals(result.getSessionDuration(), new Integer(10));
	  assertEquals(result.getSessionId(), "c377075800c4bad1031ba7eca97641c1");
	  assertEquals(result.getState(), "ANSWERED");

	  ArrayList<ActionResult> actions = result.getActions();
	  assertNotNull(result);

	  assertEquals(actions.get(0).getName(), "one");
	  assertEquals(actions.get(0).getAttempts(), new Integer(1));
	  assertEquals(actions.get(0).getDisposition(), "SUCCESS");
	  assertEquals(actions.get(0).getConfidence(), new Integer(100));
	  assertEquals(actions.get(0).getInterpretation(), "1");
	  assertEquals(actions.get(0).getUtterance(), "1");
	  assertEquals(actions.get(0).getValue(), "1");
	  assertEquals(actions.get(0).getConcept(), null);
	  assertEquals(actions.get(0).getXml(), null);
	  assertEquals(actions.get(0).getuploadStatus(), null);

	  assertEquals(actions.get(1).getName(), "two");
    assertEquals(actions.get(1).getAttempts(), new Integer(1));
    assertEquals(actions.get(1).getDisposition(), "SUCCESS");
    assertEquals(actions.get(1).getConfidence(), new Integer(100));
    assertEquals(actions.get(1).getInterpretation(), "12");
    assertEquals(actions.get(1).getUtterance(), "12");
    assertEquals(actions.get(1).getValue(), "12");
    assertEquals(actions.get(1).getConcept(), null);
    assertEquals(actions.get(1).getXml(), null);
    assertEquals(actions.get(1).getuploadStatus(), null);
	}
}
