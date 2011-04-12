package com.voxeo.tropo.actions;

import static com.voxeo.tropo.Key.VALUE;
import support.ActionSupportHandler;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys={"next","event","name","value","required"})
@RequiredKeys(keys={"event"})
public class OnAction extends JsonAction {

	private ActionSupportHandler<SayAction> sayActionSupportHandler = new ActionSupportHandler<SayAction>(SayAction.class);	

	public OnAction() {
		
		super();
		setName("on");
	}
	
	public OnAction(Key... keys) {
	
		super(keys);
		setName("on");
	}	
	
	public SayAction say(String text) {

		return say(VALUE(text));
	}
	
	public SayAction say(Key... keys) {

		return sayActionSupportHandler.build(this, keys);
	}
}
