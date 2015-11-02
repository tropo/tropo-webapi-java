package com.voxeo.tropo.actions;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.TropoException;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys={"value","mode","terminator"})
public class ChoicesAction extends JsonAction {

	public ChoicesAction() {
		
		super();
		setName("choices");
	}
	
	public ChoicesAction(Key... keys) {
	
		super(keys);
		setName("choices");
	}	
	
	@Override
	protected void validate() throws TropoException {
		
		super.validate();
		Object mode = get("mode");
		if (mode != null) {
			if (!mode.equals("dtmf") && !mode.equals("speech")) {
				throw new TropoException("If mode is provided, only 'dtmf', 'speech' or 'any' is supported");
			}
		}
	}
}
