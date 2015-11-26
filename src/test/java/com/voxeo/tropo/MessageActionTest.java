package com.voxeo.tropo;

import static com.voxeo.tropo.Key.ANSWER_ON_MEDIA;
import static com.voxeo.tropo.Key.BARGEIN;
import static com.voxeo.tropo.Key.CHANNEL;
import static com.voxeo.tropo.Key.FORMAT;
import static com.voxeo.tropo.Key.FROM;
import static com.voxeo.tropo.Key.METHOD;
import static com.voxeo.tropo.Key.NETWORK;
import static com.voxeo.tropo.Key.PASSWORD;
import static com.voxeo.tropo.Key.TIMEOUT;
import static com.voxeo.tropo.Key.TO;
import static com.voxeo.tropo.Key.URL;
import static com.voxeo.tropo.Key.USERNAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.actions.Do;
import com.voxeo.tropo.enums.Channel;
import com.voxeo.tropo.enums.Format;
import com.voxeo.tropo.enums.Network;

public class MessageActionTest {

	@Test
	public void testMessage() {

		Tropo tropo = new Tropo();
		tropo.message(TO("foo"), FROM("bar"), NETWORK(Network.SMS), CHANNEL(Channel.TEXT), TIMEOUT(10.0f), ANSWER_ON_MEDIA(false)).and(
			Do.headers(new String[]{"fooKey","fooValue"}, new String[]{"barKey","barValue"}),
			Do.startRecording(URL("http://foobar"), METHOD("POST"), FORMAT(Format.MP3), USERNAME("jose"), PASSWORD("passwd")),
			Do.say("Please say your account number"));
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"message\":{\"to\":\"foo\",\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"headers\":{\"fooKey\":\"fooValue\",\"barKey\":\"barValue\"},\"startRecording\":{\"url\":\"http://foobar\",\"method\":\"POST\",\"format\":\"audio/mp3\",\"username\":\"jose\",\"password\":\"passwd\"},\"say\":[{\"value\":\"Please say your account number\"}]}}]}");
	}
	
	@Test
	public void testMessageFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.message(BARGEIN(true), FROM("bar"), NETWORK(Network.SMS), CHANNEL(Channel.TEXT), TIMEOUT(10.0f), ANSWER_ON_MEDIA(false));			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'bargein' for action");
		}
	}

	@Test
	public void testMessageAndSay() {

		Tropo tropo = new Tropo();
		tropo.message(TO("+13055551212"), NETWORK(Network.SMS)).say("This is an announcement");
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"message\":{\"to\":\"+13055551212\",\"network\":\"SMS\",\"say\":[{\"value\":\"This is an announcement\"}]}}]}");
	}

	@Test
	public void testMessageTraditionalWithTo() {

		Tropo tropo = new Tropo();
		tropo.message("+13055551212");
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"message\":{\"to\":\"+13055551212\"}}]}");
	}

	@Test
	public void testMessageTraditionalWithToAndNetwork() {

		Tropo tropo = new Tropo();
		tropo.message("+13055551212",Network.SMS);
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"message\":{\"to\":\"+13055551212\",\"network\":\"SMS\"}}]}");
	}

	@Test
	public void testMessageTraditionalWithToAndNetworkAndFrom() {

		Tropo tropo = new Tropo();
		tropo.message("+13055551212",Network.SMS,"+13055551234");
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"message\":{\"to\":\"+13055551212\",\"network\":\"SMS\",\"from\":\"+13055551234\"}}]}");
	}

	@Test
	public void testMessageTraditionalWithToAndNetworkAndFromAndChannel() {

		Tropo tropo = new Tropo();
		tropo.message("+13055551212",Network.SMS,"+13055551234",Channel.TEXT);
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"message\":{\"to\":\"+13055551212\",\"network\":\"SMS\",\"from\":\"+13055551234\",\"channel\":\"TEXT\"}}]}");
	}	
	
	@Test
	public void testFailsMessageWithNoToParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.message(NETWORK(Network.SMS)).say("This is an announcement");
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'to'");
		}
	}
}
