package com.voxeo.tropo.actions;

import static com.voxeo.tropo.Key.VALUE;
import net.sf.json.JSONObject;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys={"name","value","text","allowSignals","voice","event","as"})
@RequiredKeys(keys={"value"})
public class SayAction extends ArrayAction {

	public SayAction(String text) {

		setName("say");
		JSONObject item = new JSONObject();
		item.put("value", text);
		add(item);		
	}	
	
	public SayAction(Key... keys) {
	
		super(keys);		
		setName("say");
	}
	
	public SayAction say(String text) {

		return say(VALUE(text));
	}
	
	public SayAction say(Key... keys) {

		add(buildObjectFromKeys(keys));		
		return this;
	}
}
