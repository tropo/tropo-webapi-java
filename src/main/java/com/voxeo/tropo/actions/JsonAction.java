package com.voxeo.tropo.actions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.TropoException;

public abstract class JsonAction extends Action {

	public JsonAction() {
		
		super(new JSONObject());
	}	
	
	public JsonAction(Key... keys) {

		this();
		addKeys(keys);
	}	

	@Override
	public void reset() {

		setNode(new JSONObject());
	}
	
	protected void addKeys(Key... keys) {

		for(Key key: keys) {
			if (!isValidKey(key)) {
				throw new TropoException(String.format("Invalid key '%s' for action",key.getName()));
			}
			put(key.getName(), key.getValue());
		}
	}	

	@SuppressWarnings("unchecked")
	protected void accumulate(String key, Action action) {
		
		JSONObject node = (JSONObject)getNode();
		if (node.get(key) == null) {
			put(key,action.json());
		} else {
			// There is already an element. Accumulate it!
			JSON json = action.json();
			if (json.isArray()) {
				JSONArray array = (JSONArray)json;
				Iterator<JSON> it = array.iterator();
				while(it.hasNext()) {
					node.accumulate(key,it.next());
				}
			} else {
				node.accumulate(key, json);
			}
		}
	}
	
	public void put(String key, Action action) {
		
		action.setParent(this);
		validate();
		put(key,action.json());
	}
	
	protected void put(String key, Object value) {
		
		((JSONObject)getNode()).put(key, value);
	}
	
	protected Object get(String key) {
		
		if (this instanceof JsonAction) {
			return ((JSONObject)getNode()).get(key);
		}
		return null;
	}
	
	private void checkFields(String... fields) throws TropoException {
		
		for (String field: fields) {
			if (get(field) == null) {
				throw new TropoException(String.format("Missing required property: '%s'",field));
			}
		}
	}
	
	protected void validate() throws TropoException {
		
		if (this instanceof JsonAction) {
			for(String key: getRequiredKeys()) {
				checkFields(key);
			}
		}
	}
	
	public void checkUrl(String url) throws TropoException {
		
		String string = (String)get(url);
		if (string != null) {
			try {
				new URL(string);
			} catch (MalformedURLException e) {
				throw new TropoException("The 'url' parameter must be a valid URL");
			}
		}
	}
}
