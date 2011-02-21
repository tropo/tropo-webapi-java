package com.voxeo.tropo;

import static com.voxeo.tropo.Key.BEEP;
import static com.voxeo.tropo.Key.EMAIL_FORMAT;
import static com.voxeo.tropo.Key.EXIT_TONE;
import static com.voxeo.tropo.Key.NAME;
import static com.voxeo.tropo.Key.SEND_TONES;
import static com.voxeo.tropo.Key.TO;
import static com.voxeo.tropo.Key.URL;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.voxeo.tropo.actions.Do;

public class TranscriptionTest {

	@Test
	public void testTranscriptionFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.record(NAME("foo"), URL("http://sendme.com/tropo"), BEEP(true), SEND_TONES(true), EXIT_TONE("#")).and(
				Do.transcription(TO("bling"), URL("mailto:jose@voxeo.com"), EMAIL_FORMAT("encoded")));
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'to' for action");
		}	
	}
}
