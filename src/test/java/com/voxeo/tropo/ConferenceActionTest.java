package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.enums.Voice;

public class ConferenceActionTest {

	@Test
	public void testConference() {

		Tropo tropo = new Tropo();
		tropo.conference(Key.ID("1234"),Key.NAME("foo"),Key.MUTE(false),Key.PLAY_TONES(false),Key.INTERDIGIT_TIMEOUT(3.5F),Key.REQUIRED(true),Key.TERMINATOR("#"));
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"conference\":{\"id\":\"1234\",\"name\":\"foo\",\"mute\":false,\"playTones\":false,\"interdigitTimeout\":3.5,\"required\":true,\"terminator\":\"#\"}}]}");
	}
	
	@Test
	public void testConferenceWithBooleanValueOnJoinAndLeavePrompt() {

		Tropo tropo = new Tropo();
		tropo.conference(Key.ID("1234"),Key.NAME("foo"),Key.MUTE(false),Key.PLAY_TONES(false),Key.JOIN_PROMPT(true),Key.LEAVE_PROMPT(true));
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"conference\":{\"id\":\"1234\",\"name\":\"foo\",\"mute\":false,\"playTones\":false,\"joinPrompt\":true,\"leavePrompt\":true}}]}");
	}
	
	@Test
	public void testConferenceWithDefaultVoiceOnJoinAndLeavePrompt() {

		Tropo tropo = new Tropo();
		tropo.conference(Key.ID("1234"),Key.NAME("foo"),Key.MUTE(false),Key.PLAY_TONES(false),Key.JOIN_PROMPT("Welcome to the conference"),Key.LEAVE_PROMPT("Someone leaves the conference"));
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"conference\":{\"id\":\"1234\",\"name\":\"foo\",\"mute\":false,\"playTones\":false,\"joinPrompt\":{\"value\":\"Welcome to the conference\"},\"leavePrompt\":{\"value\":\"Someone leaves the conference\"}}}]}");
	}
	
	@Test
	public void testConferenceWithSpecificVoiceOnJoinAndLeavePrompt() {

		Tropo tropo = new Tropo();
		tropo.conference(Key.ID("1234"),Key.NAME("foo"),Key.MUTE(false),Key.PLAY_TONES(false),Key.JOIN_PROMPT("Welcome to the conference",Voice.KATE),Key.LEAVE_PROMPT("Someone leaves the conference",Voice.KATE));
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"conference\":{\"id\":\"1234\",\"name\":\"foo\",\"mute\":false,\"playTones\":false,\"joinPrompt\":{\"voice\":\"kate\",\"value\":\"Welcome to the conference\"},\"leavePrompt\":{\"voice\":\"kate\",\"value\":\"Someone leaves the conference\"}}}]}");
	}
		
	@Test
	public void testFailsConferenceWithNoIdParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.conference(Key.NAME("bar"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'id'");
		}
	}
	
	@Test
	public void testFailsConferenceWithNoNameParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.conference(Key.ID("1234"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'name'");
		}
	}
	
	@Test
	public void testConferenceFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.conference(Key.TO("foo"),Key.ID("1234"),Key.NAME("foo"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'to' for action");
		}
	}	
	
	@Test
	public void testAllowSignals() {
		
		Tropo tropo = new Tropo();
		tropo.conference(Key.ID("1234"),Key.NAME("foo"),Key.MUTE(false),Key.PLAY_TONES(false),Key.TERMINATOR("#"),Key.ALLOW_SIGNALS("exit","quit","bye"));
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"conference\":{\"id\":\"1234\",\"name\":\"foo\",\"mute\":false,\"playTones\":false,\"terminator\":\"#\",\"allowSignals\":[\"exit\",\"quit\",\"bye\"]}}]}");
	}
	
	@Test
	public void testPromptLogSecurity() {
		
		Tropo tropo = new Tropo();
		tropo.conference(Key.ID("1234"),Key.NAME("foo"),Key.MUTE(false),Key.PLAY_TONES(false),Key.TERMINATOR("#"),Key.PROMPT_LOG_SECURITY());
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"conference\":{\"id\":\"1234\",\"name\":\"foo\",\"mute\":false,\"playTones\":false,\"terminator\":\"#\",\"promptLogSecurity\":\"suppress\"}}]}");
	}
}
