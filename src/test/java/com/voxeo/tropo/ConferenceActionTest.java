package com.voxeo.tropo;

import static com.voxeo.tropo.Key.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.actions.Do;

public class ConferenceActionTest {

	@Test
	public void testConference() {

		Tropo tropo = new Tropo();
		tropo.conference(NAME("foo"),ID("1234"),MUTE(false),SEND_TONES(false),EXIT_TONE("#"),INTERDIGIT_TIMEOUT(5));
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"conference\":{\"name\":\"foo\",\"id\":\"1234\",\"mute\":false,\"send_tones\":false,\"exit_tone\":\"#\",\"interdigitTimeout\":5}}]}");
	}
	
	@Test
	public void testConferenceWithSingleOnAndSayBlocks() {

		Tropo tropo = new Tropo();
		tropo
			.conference(NAME("foo"),ID("1234"),MUTE(false),SEND_TONES(false),EXIT_TONE("#")).and(
				Do.on(EVENT("join")).and(
					Do.say("Welcome to the conference")
				)
			);
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"conference\":{\"name\":\"foo\",\"id\":\"1234\",\"mute\":false,\"send_tones\":false,\"exit_tone\":\"#\",\"on\":[{\"event\":\"join\",\"say\":[{\"value\":\"Welcome to the conference\"}]}]}}]}");
	}
	
	@Test
	public void testConferenceWithSingleOnAndSayMethods() {

		Tropo tropo = new Tropo();
		tropo
			.conference(NAME("foo"),ID("1234"),MUTE(false),SEND_TONES(false),EXIT_TONE("#")).and(
				Do.on(EVENT("join")).say("Welcome to the conference")
			);
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"conference\":{\"name\":\"foo\",\"id\":\"1234\",\"mute\":false,\"send_tones\":false,\"exit_tone\":\"#\",\"on\":[{\"event\":\"join\",\"say\":[{\"value\":\"Welcome to the conference\"}]}]}}]}");
	}
	
	@Test
	public void testConferenceWithSeveralOnAndSayBlocks() {

		Tropo tropo = new Tropo();
		tropo
			.conference(NAME("foo"),ID("1234"),MUTE(false),SEND_TONES(false),EXIT_TONE("#")).and(
				Do.on(EVENT("join")).say("Welcome to the conference"),
				Do.on(EVENT("leave")).say("Someone has left the conference")
			);
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"conference\":{\"name\":\"foo\",\"id\":\"1234\",\"mute\":false,\"send_tones\":false,\"exit_tone\":\"#\",\"on\":[{\"event\":\"join\",\"say\":[{\"value\":\"Welcome to the conference\"}]},{\"event\":\"leave\",\"say\":[{\"value\":\"Someone has left the conference\"}]}]}}]}");
	}
		
	@Test
	public void testFailsConferenceWithNoIdParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.conference(NAME("bar"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'id'");
		}
	}
	
	@Test
	public void testConferenceFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.conference(TO("foo"),ID("1234"),MUTE(false),SEND_TONES(false),EXIT_TONE("#"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'to' for action");
		}
	}	
	
	@Test
	public void testAllowSignals() {
		
		Tropo tropo = new Tropo();
		tropo.conference(NAME("foo"),ID("1234"),MUTE(false),SEND_TONES(false),EXIT_TONE("#"),ALLOW_SIGNALS("exit","stopHold"));
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"conference\":{\"name\":\"foo\",\"id\":\"1234\",\"mute\":false,\"send_tones\":false,\"exit_tone\":\"#\",\"allowSignals\":[\"exit\",\"stopHold\"]}}]}");
	}
}
