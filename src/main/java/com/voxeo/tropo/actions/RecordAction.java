package com.voxeo.tropo.actions;

import static com.voxeo.tropo.Key.EMAIL_FORMAT;
import static com.voxeo.tropo.Key.ID;
import static com.voxeo.tropo.Key.URL;
import static com.voxeo.tropo.Key.VALUE;
import support.ActionSupportHandler;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.TropoException;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys={"name","send_tones","exit_tone","attempts","allowSignals","bargein","beep","choices","format","maxSilence","maxTime","method",
        "minConfidence","required","transcription","url","password","username","timeout","voice","interdigitTimeout", "asyncUpload"})
@RequiredKeys(keys={"url","name"})
public class RecordAction extends JsonAction {
	
	private ActionSupportHandler<SayAction> sayActionSupportHandler = new ActionSupportHandler<SayAction>(SayAction.class);	
	private ActionSupportHandler<ChoicesAction> choicesActionSupportHandler = new ActionSupportHandler<ChoicesAction>(ChoicesAction.class);	
	private ActionSupportHandler<TranscriptionAction> transcriptionActionSupportHandler = new ActionSupportHandler<TranscriptionAction>(TranscriptionAction.class);	

	public RecordAction() {
		
		super();
		setName("record");
	}
	
	public RecordAction(Key... keys) {
	
		super(keys);
		setName("record");
	}	
	
	public SayAction say(String text) {

		return say(VALUE(text));
	}
	
	public SayAction say(Key... keys) {

		return sayActionSupportHandler.build(this, keys);
	}	
	
	public ChoicesAction choices(Key... keys) {

		return choicesActionSupportHandler.build(this, keys);
	}
	
	public ChoicesAction choices(String value) {

		return choices(VALUE(value));
	}

	public TranscriptionAction transcription(Key... keys) {

		return transcriptionActionSupportHandler.build(this, keys);
	}	
	
	public TranscriptionAction transcription(String id, String url, String emailFormat) {

		return transcription(ID(id), URL(url), EMAIL_FORMAT(emailFormat));
	}
	
	@Override
	protected void validate() throws TropoException {
		
		super.validate();
		checkUrl("url");
	}
}
