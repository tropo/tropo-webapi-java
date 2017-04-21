package com.voxeo.tropo.actions;

import static com.voxeo.tropo.Key.VALUE;
import support.ActionSupportHandler;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys={"next","value","event","name","required","post"})
public class NestedOnAction extends ArrayAction {

	private ActionSupportHandler<SayAction> sayActionSupportHandler = new ActionSupportHandler<SayAction>(SayAction.class);	
	private ActionSupportHandler<AskAction> askActionSupportHandler = new ActionSupportHandler<AskAction>(AskAction.class);

	public NestedOnAction() {
		
		super();
		setName("on");
	}
	
	public NestedOnAction(Key... keys) {
	
		super(keys);
		setName("on");
	}	
	
	public SayAction say(String text) {

		return say(VALUE(text));
	}
	
	public SayAction say(Key... keys) {

		return sayActionSupportHandler.build(this, keys);
	}

  public AskAction ask(Key... keys) {

    return askActionSupportHandler.build(this, keys);
  }
}
