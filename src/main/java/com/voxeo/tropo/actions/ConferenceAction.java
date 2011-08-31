package com.voxeo.tropo.actions;

import static com.voxeo.tropo.Key.EVENT;
import static com.voxeo.tropo.Key.NEXT;
import support.ActionSupportHandler;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys={"name","allowSignals","mute","on","playTones","terminator","id","send_tones","exit_tone","interdigitTimeout"})
@RequiredKeys(keys={"id"})
public class ConferenceAction extends JsonAction {

	private ActionSupportHandler<NestedOnAction> onActionSupportHandler = new ActionSupportHandler<NestedOnAction>(NestedOnAction.class);	

	public ConferenceAction() {
		
		super();
		setName("conference");
	}
	
	public ConferenceAction(Key... keys) {
	
		super(keys);
		setName("conference");
	}
	
	public NestedOnAction on(Key... keys) {

		return onActionSupportHandler.build(this, keys);
	}
	
	public NestedOnAction on(String event, String next) {

		return on(EVENT(event), NEXT(next));
	}
}
