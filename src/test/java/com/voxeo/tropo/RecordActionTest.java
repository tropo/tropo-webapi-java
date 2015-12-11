package com.voxeo.tropo;

import static com.voxeo.tropo.Key.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.actions.Do;
import com.voxeo.tropo.actions.RecordAction;
import com.voxeo.tropo.enums.Voice;

public class RecordActionTest {
	
	@Test
	public void testRecord() {
		
		Tropo tropo = new Tropo();
		tropo.record(NAME("foo"),URL("http://sendme.com/tropo"),BEEP(true),SEND_TONES(false),EXIT_TONE("#"),INTERDIGIT_TIMEOUT(5), MAX_TIME(300.0f), ASYNC_UPLOAD(true));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"send_tones\":false,\"exit_tone\":\"#\",\"interdigitTimeout\":5,\"maxTime\":300.0,\"asyncUpload\":true}}]}");
	}	
	
	@Test
	public void testFailsRecordWithNoNameParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.record(URL("http://sendme.com/tropo"),BEEP(true),SEND_TONES(false),EXIT_TONE("#"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'name'");
		}
	}

	@Test
	public void testFailsRecordWithNoUrlParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.record(NAME("foo"),BEEP(true),SEND_TONES(false),EXIT_TONE("#"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'url'");
		}
	}


	@Test
	public void testFailsRecordWithInvalidUrls() {

		Tropo tropo = new Tropo();
		try {
			tropo.record(NAME("foo"),URL("invalid"), BEEP(true),SEND_TONES(false),EXIT_TONE("#"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(),  "The 'url' parameter must be a valid URL");
		}
	}
	
	@Test
	public void testRecordAcceptsEmailAddress() {
		
		Tropo tropo = new Tropo();
		tropo.record(NAME("foo"),URL("mailto:foo@bar.com"),BEEP(true),SEND_TONES(false),EXIT_TONE("#"));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"mailto:foo@bar.com\",\"beep\":true,\"send_tones\":false,\"exit_tone\":\"#\"}}]}");
	}

	@Test
	public void testRecordWithTranscriptionRequest() {

		Tropo tropo = new Tropo();
		tropo.record(NAME("foo"), URL("http://sendme.com/tropo"), BEEP(true), SEND_TONES(true), EXIT_TONE("#")).and(
			Do.transcription(ID("bling"), URL("mailto:jose@voxeo.com"), EMAIL_FORMAT("encoded")),
			Do.say("Please say your account number"),
			Do.choices(VALUE("[5 DIGITS]")));
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"send_tones\":true,\"exit_tone\":\"#\",\"transcription\":{\"id\":\"bling\",\"url\":\"mailto:jose@voxeo.com\",\"emailFormat\":\"encoded\"},\"say\":[{\"value\":\"Please say your account number\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
	}

	@Test
	public void testRecordFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.record(TO("foo"),URL("http://sendme.com/tropo"),BEEP(true),SEND_TONES(false),EXIT_TONE("#"));
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'to' for action");
		}
	}
	
	@Test
	public void testAllowSignals() {
		
		Tropo tropo = new Tropo();
		tropo.record(NAME("foo"),URL("http://sendme.com/tropo"),BEEP(true),SEND_TONES(false),EXIT_TONE("#"),ALLOW_SIGNALS("exit","stopHold"));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"send_tones\":false,\"exit_tone\":\"#\",\"allowSignals\":[\"exit\",\"stopHold\"]}}]}");
	}
	
	@Test
	public void testRecordTraditionalWay() {
		
		Tropo tropo = new Tropo();
		tropo.record("foo","http://sendme.com/tropo",true,false,"#");
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"send_tones\":false,\"exit_tone\":\"#\"}}]}");
	}

	@Test
	public void testRecordWithSayTranscriptionChoicesSplitted() {

		Tropo tropo = new Tropo();
		RecordAction record = tropo.record("foo","http://sendme.com/tropo",true,true,"#");
		record.transcription(ID("bling"), URL("mailto:jose@voxeo.com"), EMAIL_FORMAT("encoded"));
		record.say(VALUE("Please say your account number"));
		record.choices(VALUE("[5 DIGITS]"));
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"send_tones\":true,\"exit_tone\":\"#\",\"transcription\":{\"id\":\"bling\",\"url\":\"mailto:jose@voxeo.com\",\"emailFormat\":\"encoded\"},\"say\":[{\"value\":\"Please say your account number\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
	}

	@Test
	public void testRecordWithSayTranscriptionChoicesEvenMoreTraditionalWay() {

		Tropo tropo = new Tropo();
		RecordAction record = tropo.record("foo","http://sendme.com/tropo",true,true,"#");
		record.transcription("bling", "mailto:jose@voxeo.com", "encoded");
		record.say("Please say your account number");
		record.choices("[5 DIGITS]");
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"send_tones\":true,\"exit_tone\":\"#\",\"transcription\":{\"id\":\"bling\",\"url\":\"mailto:jose@voxeo.com\",\"emailFormat\":\"encoded\"},\"say\":[{\"value\":\"Please say your account number\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
	}
	
	
	@Test
	public void testRecordAcceptsVoice() {
		
		Tropo tropo = new Tropo();
		tropo.record(NAME("foo"),URL("http://sendme.com/tropo"),BEEP(true),SEND_TONES(false),EXIT_TONE("#"),VOICE(Voice.ALLISON));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"send_tones\":false,\"exit_tone\":\"#\",\"voice\":\"allison\"}}]}");
	}
}
