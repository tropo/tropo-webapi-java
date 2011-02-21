package com.voxeo.tropo.actions;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys={"id","url","emailFormat"})
public class TranscriptionAction extends JsonAction {

	public TranscriptionAction() {
		
		super();
		setName("transcription");
	}
	
	public TranscriptionAction(Key... keys) {
	
		super(keys);
		setName("transcription");
	}
}
