package com.voxeo.tropo.actions;

import static com.voxeo.tropo.Key.VALUE;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.TropoException;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;
import com.voxeo.tropo.enums.Voice;

import support.ActionSupportHandler;

@ValidKeys(keys={"next","event","say","post"})
@RequiredKeys(keys={"event","say"})
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

  public static class Say {

    private String value;

    private Voice voice;

    public Say(String value) {
      this(value, null);
    }

    public Say(String value, Voice voice) {

      if (value == null || value.trim().equals("")) {
        throw new TropoException("Missing required property: value of on.say");
      }
      this.value = value;
      this.voice = voice;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public Voice getVoice() {
      return voice;
    }

    public void setVoice(Voice voice) {
      this.voice = voice;
    }
  }
}
