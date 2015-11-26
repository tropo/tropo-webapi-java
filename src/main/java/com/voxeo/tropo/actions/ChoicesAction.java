package com.voxeo.tropo.actions;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.TropoException;
import com.voxeo.tropo.annotations.ValidKeys;
import com.voxeo.tropo.enums.Mode;

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
			for(Mode modeValue : Mode.values()) {
			    if(modeValue.toString().equals(mode)){
			        return;
			    }
			}
			throw new TropoException("If mode is provided, only 'dtmf', 'speech' or 'any' is supported");
		}
	}
}
