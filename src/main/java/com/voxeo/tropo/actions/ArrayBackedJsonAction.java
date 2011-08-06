package com.voxeo.tropo.actions;

import net.sf.json.JSONArray;

public abstract class ArrayBackedJsonAction extends JsonAction {
	
	public ArrayBackedJsonAction(String name) {
		
		super();
		setName(name);
		put(name, new JSONArray());
	}
	
	@Override
	public void reset() {

		super.reset();
		put(getName(), new JSONArray());
	}
	
	
	@Override
	protected void accumulate(String key, Action value) {

		addToArray(getName(), key, value);
	}
	
	protected boolean isEmpty() {
		
		JSONArray array = getJSONArray(getName());
		return array.isEmpty();
	}
}
