package com.voxeo.tropo;

import static com.voxeo.tropo.Key.EVENT;
import static com.voxeo.tropo.Key.NEXT;
import static com.voxeo.tropo.Key.TO;
import static com.voxeo.tropo.Key.URL;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.voxeo.tropo.enums.Format;


public class StartRecordingActionTest {

	@Test
	public void testStartRecording() {
		
		Tropo tropo = new Tropo();
		tropo.startRecording(URL("http://postrecording.com/tropo"));
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"startRecording\":{\"url\":\"http://postrecording.com/tropo\"}}]}");
	}	
	
	@Test
	public void testStartRecordingLargeExample() {

		Tropo tropo = new Tropo();
		tropo.on(EVENT("error"), NEXT("/error.json")); // For fatal programming errors. Log some details so we can fix it
		tropo.on(EVENT("hangup"), NEXT("/hangup.json")); // When a user hangs or call is done. We will want to log some details.
		tropo.on(EVENT("continue"), NEXT("/next.json"));
		tropo.say("Hello");
		tropo.startRecording(URL("http://heroku-voip.marksilver.net/post_audio_to_s3?filename=foo.wav&unique_id=bar"));
		// [From this point, until stop_recording(), we will record what the caller *and* the IVR say]
		tropo.say("You are now on the record.");
		// Prompt the user to incriminate themselve on-the-record
		tropo.say("Go ahead, sing-along.");
		tropo.say("http://denalidomain.com/music/keepers/HappyHappyBirthdaytoYou-Disney.mp3");
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"on\":{\"event\":\"error\",\"next\":\"/error.json\"}},{\"on\":{\"event\":\"hangup\",\"next\":\"/hangup.json\"}},{\"on\":{\"event\":\"continue\",\"next\":\"/next.json\"}},{\"say\":[{\"value\":\"Hello\"}]},{\"startRecording\":{\"url\":\"http://heroku-voip.marksilver.net/post_audio_to_s3?filename=foo.wav&unique_id=bar\"}},{\"say\":[{\"value\":\"You are now on the record.\"}]},{\"say\":[{\"value\":\"Go ahead, sing-along.\"}]},{\"say\":[{\"value\":\"http://denalidomain.com/music/keepers/HappyHappyBirthdaytoYou-Disney.mp3\"}]}]}");
	}	
	
	@Test
	public void testTransferFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.transfer(URL("tel:+14157044517"));
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'url' for action");
		}
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
	public void testStartRecordingFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.startRecording(TO("http://postrecording.com/tropo"));
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'to' for action");
		}
	}
}
