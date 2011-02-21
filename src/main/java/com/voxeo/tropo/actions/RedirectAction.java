package com.voxeo.tropo.actions;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.Tropo;
import com.voxeo.tropo.TropoException;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys={"to","from","name","required"})
@RequiredKeys(keys={"to"})
public class RedirectAction extends JsonAction {

	public RedirectAction() {
		
		super();
		setName("redirect");
	}
	
	public RedirectAction(Key... keys) {
	
		super(keys);
		setName("redirect");
	}
	
	@Override
	protected void validate() throws TropoException {

		super.validate();
		if (getParent() != null) {
			if (!(getParent() instanceof Tropo)) {
				throw new TropoException("Redirect should only be used alone and before the session is answered, use transfer instead");
			}
		}
	}
}
