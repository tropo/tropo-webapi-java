package com.voxeo.tropo.actions;

import java.util.Iterator;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.TropoException;

public abstract class ArrayAction extends Action {
	
	public ArrayAction() {
		
		super(new JSONArray());
	}	

	public ArrayAction(Key... keys) {

		this();
		add(buildObjectFromKeys(keys));
	}
	
	@Override
	public void reset() {

		setNode(new JSONArray());
	}

	protected JSONObject buildObjectFromKeys(Key... keys) {
		
		JSONObject object = new JSONObject();
		for(Key key: keys) {
			if (!isValidKey(key)) {
				throw new TropoException(String.format("Invalid key '%s' for action",key.getName()));
			}
			object.put(key.getName(), key.getValue());
		}
		return object;
	}
	
	protected void add(JSON item) {
		
		((JSONArray)getNode()).element(item);
	}
	
	public void put(String key, Action action) {
		
		append(key,action);
	}
	
	protected void append(String key, Action action) {
		
		action.validate();
		action.setParent(this);		
		JSONArray array = ((JSONArray)getNode());
		if (array.size() == 0) {
			addToArray(array, key, action);
		} else {
			JSONObject node = (JSONObject)array.get(array.size()-1);
			node.put(key,action.json());
		}
	}
	
	@Override
	protected void accumulate(String key, Action value) {

		put(key,value);
	}
	
	
	@SuppressWarnings("rawtypes")
	private void checkFields(String... fields) throws TropoException {
		
		JSONArray array = ((JSONArray)getNode());
		Iterator it = array.iterator();
		while (it.hasNext()) {
			Object element = it.next();
			if (element instanceof JSONObject) {
				for (String field: fields) {
					Object value = ((JSONObject)element).get(field);
					if (value == null) {
						throw new TropoException(String.format("Missing required property: '%s'",field));
					}
				}
			}
		}
	}
	
	protected void validate() throws TropoException {
		
		for(String key: getRequiredKeys()) {
			checkFields(key);
		}
	}
}
