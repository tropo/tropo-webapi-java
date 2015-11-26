package com.voxeo.tropo;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TropoParser {
    
    public TropoSession session(String text) throws TropoException {
    
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(text, JsonObject.class).getAsJsonObject("session");
        TropoSession session = gson.fromJson(json, TropoSession.class);
        return session;
    }
    
    public TropoLaunchResult sessionLaunch(String text) throws TropoException {
    
        if (!text.startsWith("{")) {
            text = jsonize(text);
        }
        
        return new Gson().fromJson(text, TropoLaunchResult.class);
    }
    
    private String jsonize(String text) {
    
        // For some reason the server returns A=1&B=2&... although the API states it sould
        // return JSON
        text = text.replaceAll("\\n", "");
        text = text.replaceAll("\\t", "");
        text = text.replaceAll("\\r", "");
        StringBuilder json = new StringBuilder("{");
        String[] elements = text.split("&");
        int i = 1;
        for (String element : elements) {
            String[] entry = element.split("=");
            json.append(String.format("\"%s\":\"%s\"", entry[0], entry[1]));
            if (!(i == elements.length)) {
                json.append(",");
            }
            i++;
        }
        json.append("}");
        
        return json.toString();
    }
    
    public TropoResult parse(String text) throws TropoException {
    
        try {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(text, JsonObject.class).getAsJsonObject("result");
            
            // actions may be an object or array, if it is an object,
            // we need to add this object into an array
            if (json.get("actions") instanceof JsonObject) {
                
                ActionResult actionResult = gson.fromJson(json.get("actions"), ActionResult.class);
                ArrayList<ActionResult> actions = new ArrayList<ActionResult>();
                actions.add(actionResult);
                
                // removing actions as it will cause error, while mapping to bean
                json.remove("actions");
                TropoResult result = gson.fromJson(json, TropoResult.class);
                result.setActions(actions);
                return result;
            }
            else {
                return gson.fromJson(json, TropoResult.class);
            }
            
        }
        catch (Exception e) {
            throw new TropoException(e);
        }
    }
    
    public TropoResult parse(HttpServletRequest request) {
    
        try {
            return parse(request.getReader());
        }
        catch (IOException e) {
            throw new TropoException("Could not get reader from HTTP Request", e);
        }
    }
    
    public TropoSession session(HttpServletRequest request) {
    
        try {
            return session(request.getReader());
        }
        catch (IOException e) {
            throw new TropoException("Could not get reader from HTTP Request", e);
        }
    }
    
    public TropoLaunchResult sessionLaunch(HttpServletRequest request) {
    
        try {
            return sessionLaunch(request.getReader());
        }
        catch (IOException e) {
            throw new TropoException("Could not get reader from HTTP Request", e);
        }
    }
    
    public TropoResult parse(Reader reader) {
    
        return parse(getRequestBody(reader));
    }
    
    public TropoSession session(Reader reader) {
    
        return session(getRequestBody(reader));
    }
    
    public TropoLaunchResult sessionLaunch(Reader reader) {
    
        return sessionLaunch(getRequestBody(reader));
    }
    
    private String getRequestBody(Reader reader) {
    
        return IOUtils.read(reader);
    }
    
}
