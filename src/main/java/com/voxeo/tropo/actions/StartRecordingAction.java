package com.voxeo.tropo.actions;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys={"format","method","url","username","password","asyncUpload","transcriptionOutURI","transcriptionEmailFormat","transcriptionID"})
@RequiredKeys(keys={"url"})
public class StartRecordingAction extends JsonAction {

	public StartRecordingAction() {
		
		super();
		setName("startRecording");
	}
	
	public StartRecordingAction(Key... keys) {
	
		super(keys);
		setName("startRecording");
	}	
}
