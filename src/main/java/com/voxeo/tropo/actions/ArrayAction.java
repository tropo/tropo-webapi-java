package com.voxeo.tropo.actions;

import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.voxeo.tropo.Key;
import com.voxeo.tropo.TropoException;

public abstract class ArrayAction extends Action {
	
	public ArrayAction() {
		
		super(new JsonArray());
	}	

	public ArrayAction(Key... keys) {

		this();
		add(buildObjectFromKeys(keys));
	}
	
	@Override
	public void reset() {

		setNode(new JsonArray());
	}

	protected JsonObject buildObjectFromKeys(Key... keys) {
		
		JsonObject object = new JsonObject();
		for(Key key: keys) {
			if (!isValidKey(key)) {
				throw new TropoException(String.format("Invalid key '%s' for action",key.getName()));
			}
			putProperty(object, key.getName(), key.getValue());
		}
		return object;
	}
	
	protected void putProperty(JsonObject object, String key, Object value) {
	    
        if (value instanceof Boolean) {
            object.addProperty(key, (Boolean) value);
        }
        else if (value instanceof Number) {
            object.addProperty(key, (Number) value);
        }
        else if (value instanceof Character) {
            object.addProperty(key, (Character) value);
        }
        else if (value instanceof String) {
            object.addProperty(key, (String) value);
        }
        else {
            object.add(key, new Gson().toJsonTree(value));
        }
    }
	
	protected void add(JsonElement item) {
		
		((JsonArray)getNode()).add(item);
	}
	
	public void put(String key, Action action) {
		
		append(key,action);
	}
	
	protected void append(String key, Action action) {
		
		action.validate();
		action.setParent(this);		
		JsonArray array = ((JsonArray)getNode());
		if (array.size() == 0) {
			addToArray(array, key, action);
		} else {
			JsonObject node = (JsonObject)array.get(array.size()-1);
			node.add(key,action.json());
		}
	}
	
	@Override
	protected void accumulate(String key, Action value) {

		put(key,value);
	}
	
	
	@SuppressWarnings("rawtypes")
	private void checkFields(String... fields) throws TropoException {
		
		JsonArray array = ((JsonArray)getNode());
		Iterator it = array.iterator();
		while (it.hasNext()) {
			Object element = it.next();
			if (element instanceof JsonObject) {
				for (String field: fields) {
					Object value = ((JsonObject)element).get(field);
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
