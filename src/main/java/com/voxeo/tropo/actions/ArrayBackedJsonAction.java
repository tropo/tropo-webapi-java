package com.voxeo.tropo.actions;

import com.google.gson.JsonArray;

public abstract class ArrayBackedJsonAction extends JsonAction {
    
    public ArrayBackedJsonAction(String name) {
    
        super();
        setName(name);
        put(getName(), new JsonArray());
    }
    
    @Override
    public void reset() {
    
        super.reset();
        put(getName(), new JsonArray());
    }
    
    @Override
    protected void accumulate(String key, Action value) {
    
        addToArray(getName(), key, value);
    }
    
    protected boolean isEmpty() {
    
        JsonArray array = getJSONArray(getName());
        return array.size() == 0;
    }
}
