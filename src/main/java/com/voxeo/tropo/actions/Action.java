package com.voxeo.tropo.actions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

public abstract class Action {
    
    private JsonElement node;
    private String name;
    private Action parent;
    
    protected Action(JsonElement node) {
    
        this.node = node;
    }
    
    protected JsonElement getNode() {
    
        return node;
    }
    
    protected void setNode(JsonElement node) {
    
        this.node = node;
    }
    
    JsonObject getJSONObject(String key) {
    
        if (!node.isJsonArray()) {
            return ((JsonObject) node).getAsJsonObject(key);
        }
        return null;
    }
    
    JsonArray getJSONArray(String key) {
    
        if (!node.isJsonArray()) {
            return ((JsonObject) node).getAsJsonArray(key);
        }
        return null;
    }
    
    public void addToArray(String arrayName, String key, Action action) {
    
        addToArray(getJSONArray(arrayName), key, action);
    }
    
    protected void addToArray(JsonArray array, String key, Action action) {
    
        action.validate();
        JsonObject node = new JsonObject();
        node.add(key, action.json());
        
        array.add(node);
        action.node = array.get(array.size()-1).getAsJsonObject().get(key);
    }
    
    protected void addNull(String arrayName, String key) {
    
        JsonArray array = getJSONArray(arrayName);
        JsonObject node = new JsonObject();
        node.add(key, null);
        array.add(node);
    }
    
    public Action and(Action... actions) {
    
        validate();
        for (Action action : actions) {
            while (action.getParent() != null) {
                action = action.getParent();
                // Nested actions support, e.j. and(on(...).say(...),...)
            }
            action.setParent(this);
            action.validate();
            accumulate(action.getName(), action);
        }
        return this;
    }
    
    public abstract void put(String key, Action value);
    
    protected abstract void accumulate(String key, Action value);
    
    public abstract void reset();
    
    protected abstract void validate();
    
    public JsonElement json() {
    
        return node;
    }
    
    public String text() {
    
        return node.toString();
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
        }
        else {
            return Collections.EMPTY_LIST;
        }
    }
    
    @SuppressWarnings("unchecked")
    protected List<String> getRequiredKeys() {
    
        RequiredKeys requiredKeysAnnotation = getClass().getAnnotation(RequiredKeys.class);
        if (requiredKeysAnnotation != null && requiredKeysAnnotation.keys() != null) {
            return Arrays.asList(requiredKeysAnnotation.keys());
        }
        else {
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
