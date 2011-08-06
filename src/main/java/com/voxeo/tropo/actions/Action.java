package com.voxeo.tropo.actions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

public abstract class Action {

	private JSON node;
	private String name;
	private Action parent;
	
	protected Action(JSON node) {
		
		this.node = node;
	}
	
	protected JSON getNode() {
		
		return node;
	}
		
	protected void setNode(JSON node) {
		
		this.node = node;
	}
	
	JSONObject getJSONObject(String key) {

		if (!node.isArray()) {
			return ((JSONObject)node).getJSONObject(key);
		}
		return null;
	}
	
	JSONArray getJSONArray(String key) {

		if (!node.isArray()) {
			return ((JSONObject)node).getJSONArray(key);
		}
		return null;
	}
	
	public void addToArray(String arrayName, String key, Action action) {
		
		addToArray(getJSONArray(arrayName),key,action);
	}

	protected void addToArray(JSONArray array, String key, Action action) {
		
		action.validate();
		JSONObject node = new JSONObject();
		node.put(key,action.json());
		
		array.element(node);
		// Element, creates a copy. If we want to keep an updatable reference we need to update the ref. link
		JSON addedNode = (JSON)array.getJSONObject((array.size()-1)).get(key);
		action.node = addedNode;
	}

	protected void addNull(String arrayName, String key) {
		
		JSONArray array = getJSONArray(arrayName);
		JSONObject node = new JSONObject();
		node.put(key,"null");
		
		array.element(node);
	}
	
	public Action and(Action... actions) {
		
		validate();
		for (Action action: actions) {
			while (action.getParent() != null) {
				action = action.getParent(); // Nested actions support, e.j. and(on(...).say(...),...)
			}			
			action.setParent(this);
			action.validate();
			accumulate(action.getName(),action);
		}
		return this;
	}	
	
	public abstract void put(String key, Action value);
	protected abstract void accumulate(String key, Action value);
	public abstract void reset();
	protected abstract void validate();
	
	public JSON json() {
		
		return node;
	}
	
	public String text() {

		String text = node.toString();
		// See Key.CHOICES to know more about this required change
		text = text.replace("$[$", "[");
		text = text.replace("${$", "{");
		//System.out.println(text);
		return text;
	}	
	
	protected boolean isValidKey(Key key) {
		
		List<String> keys = getValidKeys();
		if (keys.isEmpty()) {
			return true;
		}
		return keys.contains(key.getName());
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getValidKeys() {
		
		ValidKeys validKeysAnnotation = getClass().getAnnotation(ValidKeys.class);
		if (validKeysAnnotation != null && validKeysAnnotation.keys() != null) {
			return Arrays.asList(validKeysAnnotation.keys());
		} else {
			return Collections.EMPTY_LIST;
		}
	}
	
	@SuppressWarnings("unchecked")
	protected List<String> getRequiredKeys() {		
		
		RequiredKeys requiredKeysAnnotation = getClass().getAnnotation(RequiredKeys.class);
		if (requiredKeysAnnotation != null && requiredKeysAnnotation.keys() != null) {
			return Arrays.asList(requiredKeysAnnotation.keys());
		} else {
			return Collections.EMPTY_LIST;
		}	
	}
	
	@Override
	public String toString() {

		return text();
	}
	
	protected void setName(String name) {
		
		this.name = name;
	}
	
	public String getName() {
		
		return name;
	}
	
	protected Action getParent() {
		
		return parent;
	}
	
	protected void setParent(Action parent) {
		
		this.parent = parent;
	}
}
