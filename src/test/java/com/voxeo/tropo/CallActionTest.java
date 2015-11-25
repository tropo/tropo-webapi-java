package com.voxeo.tropo;

import static com.voxeo.tropo.Key.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.actions.CallAction;
import com.voxeo.tropo.actions.Do;
import com.voxeo.tropo.enums.Channel;
import com.voxeo.tropo.enums.Format;
import com.voxeo.tropo.enums.Network;

public class CallActionTest {

	@Test
	public void testCall() {

		Tropo tropo = new Tropo();
		tropo.call(TO("foo"), FROM("bar"), NETWORK(Network.SMS), CHANNEL(Channel.TEXT), TIMEOUT(10.0f), ANSWER_ON_MEDIA(false)).and(
			Do.headers(new String[]{"fooKey","fooValue"}, new String[]{"barKey","barValue"}),
			Do.startRecording(URL("http://foobar"), METHOD("POST"), FORMAT(Format.MP3), USERNAME("jose"), PASSWORD("passwd")));
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"to\":\"foo\",\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"headers\":{\"fooKey\":\"fooValue\",\"barKey\":\"barValue\"},\"startRecording\":{\"url\":\"http://foobar\",\"method\":\"POST\",\"format\":\"audio/mp3\",\"username\":\"jose\",\"password\":\"passwd\"}}}]}");
	}

	@Test
	public void testCallFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.call(BARGEIN(true), FROM("bar"), NETWORK(Network.SMS), CHANNEL(Channel.TEXT), TIMEOUT(10.0f), ANSWER_ON_MEDIA(false));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'bargein' for action");
		}
	}	
		
	@Test
	public void testAllowSignals() {
		
		Tropo tropo = new Tropo();
		tropo.call(TO("foo"), FROM("bar"), NETWORK(Network.SMS), CHANNEL(Channel.TEXT), TIMEOUT(10.0f), ANSWER_ON_MEDIA(false),ALLOW_SIGNALS("exit","stopHold"));
	
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"to\":\"foo\",\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"allowSignals\":[\"exit\",\"stopHold\"]}}]}");
	}
	
	@Test
	public void testCallwithToArray() {
		
		Tropo tropo = new Tropo();
		tropo.call(TO("+14155551212","+15105551212"), FROM("bar"), NETWORK(Network.SMS), CHANNEL(Channel.TEXT), TIMEOUT(10.0f), ANSWER_ON_MEDIA(false),ALLOW_SIGNALS("exit","stopHold"));
	
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"to\":[\"+14155551212\",\"+15105551212\"],\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"allowSignals\":[\"exit\",\"stopHold\"]}}]}");
	}	

	@Test
	public void testCallTraditionalWithTo() {

		Tropo tropo = new Tropo();
		tropo.call("+13055551212");
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"to\":\"+13055551212\"}}]}");
	}

	@Test
	public void testCallTraditionalWithToAndNetwork() {

		Tropo tropo = new Tropo();
		tropo.call("+13055551212",Network.SMS);
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"to\":\"+13055551212\",\"network\":\"SMS\"}}]}");
	}

	@Test
	public void testCallTraditionalWithToAndNetworkAndFrom() {

		Tropo tropo = new Tropo();
		tropo.call("+13055551212",Network.SMS,"+13055551234");
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"to\":\"+13055551212\",\"network\":\"SMS\",\"from\":\"+13055551234\"}}]}");
	}

	@Test
	public void testCallTraditionalWithToAndNetworkAndFromAndChannel() {

		Tropo tropo = new Tropo();
		tropo.call("+13055551212",Network.SMS,"+13055551234",Channel.TEXT);
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"to\":\"+13055551212\",\"network\":\"SMS\",\"from\":\"+13055551234\",\"channel\":\"TEXT\"}}]}");
	}	
	
	@Test
	public void testFailsCallWithNoToParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.call(NETWORK(Network.SMS));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'to'");
		}
	}

	@Test
	public void testCallWithHeadersTraditional() {

		Tropo tropo = new Tropo();
		CallAction call = tropo.call(TO("foo"), FROM("bar"), NETWORK(Network.SMS), CHANNEL(Channel.TEXT), TIMEOUT(10.0f), ANSWER_ON_MEDIA(false));
		call.headers(new String[]{"fooKey","fooValue"}, new String[]{"barKey","barValue"});
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"to\":\"foo\",\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"headers\":{\"fooKey\":\"fooValue\",\"barKey\":\"barValue\"}}}]}");
	}

	@Test
	public void testCallWithStartRecordingTraditional() {

		Tropo tropo = new Tropo();
		CallAction call = tropo.call(TO("foo"), FROM("bar"), NETWORK(Network.SMS), CHANNEL(Channel.TEXT), TIMEOUT(10.0f), ANSWER_ON_MEDIA(false));
		call.startRecording(URL("http://foobar"), METHOD("POST"), FORMAT(Format.MP3), USERNAME("jose"), PASSWORD("passwd"));
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"to\":\"foo\",\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"startRecording\":{\"url\":\"http://foobar\",\"method\":\"POST\",\"format\":\"audio/mp3\",\"username\":\"jose\",\"password\":\"passwd\"}}}]}");
	}
}
