package com.voxeo.tropo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SessionLauncher {

	public TropoLaunchResult launchSession(String baseUrl, String token, Map<String, String> mapParams) {
		
		if (mapParams == null) {
			mapParams = new HashMap<String, String>();
		}
		String url = baseUrl + "sessions?action=create";

		TropoParser parser = new TropoParser();
	    HttpClient client = new HttpClient();
	    client.getParams().setParameter("http.useragent", "Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4");
	    PostMethod method = new PostMethod(url);

	    NameValuePair[] params = new NameValuePair[mapParams.size()+1];
	    params[0] = new NameValuePair("token",token);
	    int i = 1;
		for (Map.Entry<String, String> entry: mapParams.entrySet()) {
			params[i] = new NameValuePair(entry.getKey(), entry.getValue());
			i++;
		}
	    method.setRequestBody(params);
	    
	    method.setRequestHeader(new Header("User-Agent", "Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4"));
	    method.setRequestHeader(new Header("Accept", "application/json"));
	    method.setRequestHeader(new Header("Content-Type", "application/x-www-form-urlencoded"));
	    
	    BufferedReader reader = null;
	    try {

	    	int returnCode = client.executeMethod(method);
	    	if (returnCode != HttpStatus.SC_OK) {
	    		throw new TropoException(String.format("Server returned with error code: %s", returnCode));
	    	}
	        reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
	        return parser.sessionLaunch(reader);
	    } catch (Exception e) {
	    	throw new TropoException("Could not launch session", e);
	    } finally {
	      method.releaseConnection();
	      if(reader != null) try { reader.close(); } catch (Exception fe) {}
	    }
	}
}
