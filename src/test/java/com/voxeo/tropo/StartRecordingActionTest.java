package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.actions.RecordAction.Url;
import com.voxeo.tropo.enums.EmailFormat;
import com.voxeo.tropo.enums.Format;
import com.voxeo.tropo.enums.Method;


public class StartRecordingActionTest {

	@Test
	public void testStartRecording() {
		
		Tropo tropo = new Tropo();
		tropo.startRecording(Key.URL("http://postrecording.com/tropo"), Key.METHOD(Method.POST));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"startRecording\":{\"url\":\"http://postrecording.com/tropo\",\"method\":\"POST\"}}]}");
	}	
	
	@Test
	public void testStartRecordingWithTranscription() {
		
		Tropo tropo = new Tropo();
		tropo.startRecording(Key.URL("http://postrecording.com/tropo"),Key.TRANSCRIPTION_OUT_URI("mailto:xxx@url.com"),Key.TRANSCRIPTION_EMAIL_FORMAT(EmailFormat.ENCODED),Key.TRANSCRIPTION_ID("1234"),Key.TRANSCRIPTION_LANGUAGE("pt-br"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"startRecording\":{\"url\":\"http://postrecording.com/tropo\",\"transcriptionOutURI\":\"mailto:xxx@url.com\",\"transcriptionEmailFormat\":\"encoded\",\"transcriptionID\":\"1234\",\"transcriptionLanguage\":\"pt-br\"}}]}");
	}	
	
	@Test
	public void testStartRecordingWithAsyncUpload() {
		
		Tropo tropo = new Tropo();
		tropo.startRecording(Key.URL("http://postrecording.com/tropo"),Key.ASYNC_UPLOAD(true));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"startRecording\":{\"url\":\"http://postrecording.com/tropo\",\"asyncUpload\":true}}]}");
	}
	
	@Test
	public void testStartRecordingLargeExample() {

		Tropo tropo = new Tropo();
		tropo.on(Key.EVENT("error"), Key.NEXT("/error.json"), Key.SAY_OF_ON("error")); // For fatal programming errors. Log some details so we can fix it
		tropo.on(Key.EVENT("hangup"), Key.NEXT("/hangup.json"),Key.SAY_OF_ON("hangup")); // When a user hangs or call is done. We will want to log some details.
		tropo.on(Key.EVENT("continue"), Key.NEXT("/next.json"), Key.SAY_OF_ON("continue"));
		tropo.say("Hello", "say");
		tropo.startRecording(Key.URL("http://heroku-voip.marksilver.net/post_audio_to_s3?filename=foo.wav&unique_id=bar"));
		// [From this point, until stop_recording(), we will record what the caller *and* the IVR say]
		tropo.say("You are now on the record.", "say");
		// Prompt the user to incriminate themselve on-the-record
		tropo.say("Go ahead, sing-along.", "say");
		tropo.say("http://denalidomain.com/music/keepers/HappyHappyBirthdaytoYou-Disney.mp3", "say");
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"on\":{\"event\":\"error\",\"next\":\"/error.json\",\"say\":[{\"value\":\"error\"}]}},{\"on\":{\"event\":\"hangup\",\"next\":\"/hangup.json\",\"say\":[{\"value\":\"hangup\"}]}},{\"on\":{\"event\":\"continue\",\"next\":\"/next.json\",\"say\":[{\"value\":\"continue\"}]}},{\"say\":[{\"value\":\"Hello\",\"name\":\"say\"}]},{\"startRecording\":{\"url\":\"http://heroku-voip.marksilver.net/post_audio_to_s3?filename=foo.wav&unique_id=bar\"}},{\"say\":[{\"value\":\"You are now on the record.\",\"name\":\"say\"}]},{\"say\":[{\"value\":\"Go ahead, sing-along.\",\"name\":\"say\"}]},{\"say\":[{\"value\":\"http://denalidomain.com/music/keepers/HappyHappyBirthdaytoYou-Disney.mp3\",\"name\":\"say\"}]}]}");
	}	
	
	@Test
	public void testStartTraditionalWay() {
		
		Tropo tropo = new Tropo();
		tropo.startRecording("http://postrecording.com/tropo");
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"startRecording\":{\"url\":\"http://postrecording.com/tropo\"}}]}");
	}
	
	@Test
	public void testStartTraditionalWay2() {
		
		Tropo tropo = new Tropo();
		tropo.startRecording("http://postrecording.com/tropo",Format.MP3);
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"startRecording\":{\"url\":\"http://postrecording.com/tropo\",\"format\":\"audio/mp3\"}}]}");
	}
	
	@Test
	public void testStartTraditionalWay3() {
		
		Tropo tropo = new Tropo();
		tropo.startRecording("http://postrecording.com/tropo",Format.MP3,"martin","pass");
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"startRecording\":{\"url\":\"http://postrecording.com/tropo\",\"format\":\"audio/mp3\",\"username\":\"martin\",\"password\":\"pass\"}}]}");
	}
	
	@Test
	public void testFailsStartRecordingWithNoUrlParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.startRecording(Key.ASYNC_UPLOAD(true));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'url'");
		}
	}
	
	@Test
	public void testStartRecordingFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.startRecording(Key.TO("http://postrecording.com/tropo"));
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'to' for action");
		}
	}

	@Test
  public void testStartRecordingAndUrl() {
    
    Tropo tropo = new Tropo();
    tropo.startRecording(Key.URL(new Url("http://postrecording.com/tropo")), Key.METHOD(Method.POST));
    
    assertEquals(tropo.text(), "{\"tropo\":[{\"startRecording\":{\"url\":{\"url\":\"http://postrecording.com/tropo\"},\"method\":\"POST\"}}]}");
  }

	@Test
  public void testStartRecordingAndUrl1() {
    
    Tropo tropo = new Tropo();
    tropo.startRecording(Key.URL("http://postrecording.com/tropo", "http://postrecording1.com/tropo"), Key.METHOD(Method.POST));
    
    assertEquals(tropo.text(), "{\"tropo\":[{\"startRecording\":{\"url\":[{\"url\":\"http://postrecording.com/tropo\"},{\"url\":\"http://postrecording1.com/tropo\"}],\"method\":\"POST\"}}]}");
  }
	@Test
  public void testStartRecordingAndUr2() {
    
    Tropo tropo = new Tropo();
    tropo.startRecording(Key.URL(new Url("http://postrecording.com/tropo"), new Url("http://postrecording1.com/tropo")), Key.METHOD(Method.POST));
    
    assertEquals(tropo.text(), "{\"tropo\":[{\"startRecording\":{\"url\":[{\"url\":\"http://postrecording.com/tropo\"},{\"url\":\"http://postrecording1.com/tropo\"}],\"method\":\"POST\"}}]}");
  }
}
