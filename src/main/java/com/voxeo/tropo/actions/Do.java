package com.voxeo.tropo.actions;

import com.voxeo.tropo.Key;

/**
 * @author martin
 *
 */
public class Do {
		
	public static ChoicesAction choices(Key... keys) {

		return new ChoicesAction(keys);
	}
	
	public static NestedOnAction on(Key... keys) {

		return new NestedOnAction(keys);
	}	

	public static NestedOnAction on(String event, String next) {

		return new NestedOnAction(Key.EVENT(event), Key.NEXT(next));
	}
	
	public static SayAction say(Key... keys) {

		return new SayAction(keys);
	}
	
	public static SayAction say(String text, String name) {

		return say(Key.VALUE(text), Key.NAME(name));
	}
	
	public static StartRecordingAction startRecording(Key... keys) {

		return new StartRecordingAction(keys);
	}	
	
	public static HeadersAction headers(String[]... headers) {

		return new HeadersAction(headers);
	}
	
	public static TranscriptionAction transcription(Key... keys) {

		return new TranscriptionAction(keys);
	}
	
	public static RedirectAction redirect(Key... keys) {

		return new RedirectAction(keys);
	}
}
