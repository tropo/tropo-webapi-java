package com.voxeo.tropo.actions;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

/**
 * <p>
 * Allows Tropo applications to begin recording the current session. The
 * resulting recording may then be sent via FTP or an HTTP POST/Multipart Form.
 * </p>
 * <p>
 * The audio file can also be transcribed and the text returned to you via an
 * email address or HTTP POST/Multipart Form - currently, transcription only
 * supports US English.
 * </p>
 */
@ValidKeys(keys={"format","method","url","username","password","asyncUpload","transcriptionOutURI","transcriptionEmailFormat","transcriptionID","transcriptionLanguage"})
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
