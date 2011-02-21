package com.voxeo.tropo;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.bean.BeanMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@SuppressWarnings("rawtypes")
public class TropoParser {

	public TropoSession session(String text) throws TropoException {
		
		JSONObject json = JSONObject.fromObject(text).getJSONObject("session");
		TropoSession session = (TropoSession) JSONObject.toBean( json, TropoSession.class);
		
		return session;
	}
	
	public TropoLaunchResult sessionLaunch(String text) throws TropoException {
		
		if (!text.startsWith("{")) {
			text = jsonize(text);
		}
		
		JSONObject json = JSONObject.fromObject(text);
		TropoLaunchResult sessionLaunch = (TropoLaunchResult) JSONObject.toBean( json, TropoLaunchResult.class);
		
		return sessionLaunch;
	}
	
	private String jsonize(String text) {

		// For some reason the server returns A=1&B=2&... although the API states it sould return JSON
		text = text.replaceAll("\\n","");
		text = text.replaceAll("\\t","");
		text = text.replaceAll("\\r","");
		StringBuilder json = new StringBuilder("{");
		String[] elements = text.split("&");
		int i=1;
		for (String element: elements) {
			String[] entry = element.split("=");
			json.append(String.format("\"%s\":\"%s\"",entry[0],entry[1]));
			if (!(i == elements.length)) {
				json.append(",");
			}
			i++;
		}
		json.append("}");

		return json.toString();
	}

	@SuppressWarnings("unchecked")
	public TropoResult parse(String text) throws TropoException {
		
		try {
			JSONObject json = JSONObject.fromObject(text).getJSONObject("result");
			
			if (json.get("actions") instanceof JSONArray) {
				Map classMap = new HashMap<String, ActionResult>();  
				classMap.put( "actions", ActionResult.class );  
				TropoResult result = (TropoResult) JSONObject.toBean( json, TropoResult.class, classMap);
				
				MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
				Morpher dynaMorpher = new BeanMorpher( ActionResult.class, morpherRegistry );  
				morpherRegistry.registerMorpher( dynaMorpher );  
				ArrayList<ActionResult> output = new ArrayList<ActionResult>();
				for (ActionResult action: result.getActions()) {
					output.add((ActionResult)morpherRegistry.morph(ActionResult.class,action));
				}
				result.setActions(output);
				return result;
			} else {
				TropoResult result = (TropoResult) JSONObject.toBean( json, TropoResult.class);
				ActionResult actionResult = (ActionResult) JSONObject.toBean(json.getJSONObject("actions") , ActionResult.class);
				ArrayList<ActionResult> actions = new ArrayList<ActionResult>();
				actions.add(actionResult);
				result.setActions(actions);
				return result;
			}
		} catch (Exception e) {
			throw new TropoException(e);
		}
	}

	public Map parse(Map map) {
		
		return JSONObject.fromObject(map);
	}
	
	public TropoResult parse(HttpServletRequest request) {
		
		try {
			return parse(request.getReader());
		} catch (IOException e) {
			throw new TropoException("Could not get reader from HTTP Request", e);
		}
	}
	
	public TropoSession session(HttpServletRequest request) {

		try {
			return session(request.getReader());
		} catch (IOException e) {
			throw new TropoException("Could not get reader from HTTP Request", e);
		}
	}
	
	public TropoLaunchResult sessionLaunch(HttpServletRequest request) {

		try {
			return sessionLaunch(request.getReader());
		} catch (IOException e) {
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
