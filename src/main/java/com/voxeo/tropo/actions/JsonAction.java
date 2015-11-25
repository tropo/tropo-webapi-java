package com.voxeo.tropo.actions;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.voxeo.tropo.Key;
import com.voxeo.tropo.TropoException;

public abstract class JsonAction extends Action {
    
    public JsonAction() {
    
        super(new JsonObject());
    }
    
    public JsonAction(Key... keys) {
    
        this();
        addKeys(keys);
    }
    
    @Override
    public void reset() {
    
        setNode(new JsonObject());
    }
    
    protected void addKeys(Key... keys) {
    
        for (Key key : keys) {
            if (!isValidKey(key)) {
                throw new TropoException(String.format("Invalid key '%s' for action", key.getName()));
            }
            putProperty(key.getName(), key.getValue());
        }
    }
    
    protected void accumulate(String key, Action action) {
    
        JsonObject node = (JsonObject) getNode();
        JsonElement oldKeyElement = node.get(key);
        if (oldKeyElement == null) {
            put(key, action.json());
        }
        else if (oldKeyElement.isJsonArray()) {
            // There is already an json array element. Accumulate it!
            JsonElement json = action.json();
            if (json.isJsonArray()) {
                ((JsonArray) oldKeyElement).addAll((JsonArray) json);
            }
            else {
                ((JsonArray) oldKeyElement).add(json);
            }
        }
        else {
            // There is already an json object element. Accumulate it!
            JsonElement json = action.json();
            JsonArray newArray = new JsonArray();
            newArray.add(oldKeyElement);
            if (json.isJsonArray()) {
                newArray.addAll((JsonArray) json);
            }
            else {
                newArray.add(json);
            }
            node.add(key, newArray);
        }
    }
    
    public void put(String key, Action action) {
    
        action.setParent(this);
        validate();
        put(key, action.json());
    }
    
    protected void putProperty(String key, Object value) {
    
        if (value instanceof Boolean) {
            ((JsonObject) getNode()).addProperty(key, (Boolean) value);
        }
        else if (value instanceof Number) {
            ((JsonObject) getNode()).addProperty(key, (Number) value);
        }
        else if (value instanceof Character) {
            ((JsonObject) getNode()).addProperty(key, (Character) value);
        }
        else if (value instanceof String) {
            ((JsonObject) getNode()).addProperty(key, (String) value);
        }
        else {
            ((JsonObject) getNode()).add(key, new Gson().toJsonTree(value));
        }
    }
    
    protected void put(String key, JsonElement value) {
    
        ((JsonObject) getNode()).add(key, value);
    }
    
    protected Object get(String key) {
    
        if (this instanceof JsonAction) {
            JsonObject node = (JsonObject) getNode();
            JsonElement element = node.get(key);
            
            if (element instanceof JsonPrimitive) {
                JsonPrimitive primitive = (JsonPrimitive)element;
                if(primitive.isBoolean()){
                    
                    return primitive.getAsBoolean();
                }
                else if(primitive.isNumber()){
                    return primitive.getAsNumber();
                }
                else{
                    return primitive.getAsString();
                }
            }
            else {
                return element;
            }
            
        }
        return null;
    }
    
    private void checkFields(String... fields) throws TropoException {
    
        for (String field : fields) {
            if (get(field) == null) {
                throw new TropoException(String.format("Missing required property: '%s'", field));
            }
        }
    }
    
    protected void validate() throws TropoException {
    
        if (this instanceof JsonAction) {
            for (String key : getRequiredKeys()) {
                checkFields(key);
            }
        }
    }
    
    public void checkUrl(String url) throws TropoException {
    
        String string = (String) get(url);
        if (string != null) {
            try {
                new URL(string);
            }
            catch (MalformedURLException e) {
                throw new TropoException("The 'url' parameter must be a valid URL");
            }
        }
    }
}
