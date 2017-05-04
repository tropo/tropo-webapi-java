package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.actions.Do;
import com.voxeo.tropo.actions.RecordAction;
import com.voxeo.tropo.actions.RecordAction.Say;
import com.voxeo.tropo.enums.Voice;

public class RecordActionTest {
	
	@Test
	public void testRecord() {
		
		Tropo tropo = new Tropo();
		tropo.record(Key.NAME("foo"),Key.URL("http://sendme.com/tropo"),Key.BEEP(true),Key.INTERDIGIT_TIMEOUT(5f), Key.MAX_TIME(300.0f), Key.ASYNC_UPLOAD(true));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"interdigitTimeout\":5.0,\"maxTime\":300.0,\"asyncUpload\":true}}]}");
	}	
	
	@Test
	public void testFailsRecordWithNoNameParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.record(Key.URL("http://sendme.com/tropo"),Key.BEEP(true));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'name'");
		}
	}

	@Test
	public void testFailsRecordWithNoUrlParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.record(Key.NAME("foo"),Key.BEEP(true));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'url'");
		}
	}


	@Test
	public void testFailsRecordWithInvalidUrls() {

		Tropo tropo = new Tropo();
		try {
			tropo.record(Key.NAME("foo"),Key.URL("invalid"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(),  "The 'url' parameter must be a valid URL");
		}
	}

	@Test
  public void testFailsRecordWithInvalidSay() {

    Tropo tropo = new Tropo();
    try {
      tropo.record(Key.NAME("foo"),Key.URL("http://sendme.com/tropo"),Key.SAY_OF_RECORD(new Say(null)));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(),  "Missing required property: value of record.say");
    }
  }

	@Test
  public void testFailsRecordWithInvalidSay1() {

    Tropo tropo = new Tropo();
    try {
      tropo.record(Key.NAME("foo"),Key.URL("http://sendme.com/tropo"),Key.SAY_OF_RECORD(new Say("")));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(),  "Missing required property: value of record.say");
    }
  }

	@Test
  public void testFailsRecordWithInvalidSay2() {

    Tropo tropo = new Tropo();
    try {
      tropo.record(Key.NAME("foo"),Key.URL("http://sendme.com/tropo"),Key.SAY_OF_RECORD(new Say("Sorry, I did not hear anything. Please call back.","nomatch:1")));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(),  "For record, the only possible event is 'timeout'.");
    }
  }
	
	@Test
	public void testRecordWithTranscriptionRequest() {

		Tropo tropo = new Tropo();
		tropo.record(Key.NAME("foo"), Key.URL("http://sendme.com/tropo"), Key.BEEP(true)).and(
			Do.transcription(Key.ID("bling"), Key.URL("mailto:jose@voxeo.com"), Key.EMAIL_FORMAT("encoded")),
			Do.say("Please say your account number","say"),
			Do.choices(Key.VALUE("[5 DIGITS]")));
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"transcription\":{\"id\":\"bling\",\"url\":\"mailto:jose@voxeo.com\",\"emailFormat\":\"encoded\"},\"say\":[{\"value\":\"Please say your account number\",\"name\":\"say\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
	}

	@Test
	public void testRecordFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.record(Key.TO("foo"),Key.URL("http://sendme.com/tropo"),Key.BEEP(true));
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'to' for action");
		}
	}
	
	@Test
	public void testAllowSignals() {
		
		Tropo tropo = new Tropo();
		tropo.record(Key.NAME("foo"),Key.URL("http://sendme.com/tropo"),Key.BEEP(true),Key.ALLOW_SIGNALS("exit","stopHold"));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"allowSignals\":[\"exit\",\"stopHold\"]}}]}");
	}
	
	@Test
	public void testRecordTraditionalWay() {
		
		Tropo tropo = new Tropo();
		tropo.record("foo","http://sendme.com/tropo",true);
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true}}]}");
	}

	@Test
	public void testRecordWithSayTranscriptionChoicesSplitted() {

		Tropo tropo = new Tropo();
		RecordAction record = tropo.record("foo","http://sendme.com/tropo",true);
		record.transcription(Key.ID("bling"), Key.URL("mailto:jose@voxeo.com"), Key.EMAIL_FORMAT("encoded"));
		record.say(Key.VALUE("Please say your account number"));
		record.choices(Key.VALUE("[5 DIGITS]"));
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"transcription\":{\"id\":\"bling\",\"url\":\"mailto:jose@voxeo.com\",\"emailFormat\":\"encoded\"},\"say\":[{\"value\":\"Please say your account number\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
	}

	@Test
	public void testRecordWithSayTranscriptionChoicesEvenMoreTraditionalWay() {

		Tropo tropo = new Tropo();
		RecordAction record = tropo.record("foo","http://sendme.com/tropo",true);
		record.transcription("bling", "mailto:jose@voxeo.com", "encoded");
		record.say("Please say your account number");
		record.choices("[5 DIGITS]");
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"transcription\":{\"id\":\"bling\",\"url\":\"mailto:jose@voxeo.com\",\"emailFormat\":\"encoded\"},\"say\":[{\"value\":\"Please say your account number\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
	}
	
	
	@Test
	public void testRecordAcceptsVoice() {
		
		Tropo tropo = new Tropo();
		tropo.record(Key.NAME("foo"),Key.URL("http://sendme.com/tropo"),Key.BEEP(true),Key.VOICE(Voice.ALLISON));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"record\":{\"name\":\"foo\",\"url\":\"http://sendme.com/tropo\",\"beep\":true,\"voice\":\"allison\"}}]}");
	}
}
