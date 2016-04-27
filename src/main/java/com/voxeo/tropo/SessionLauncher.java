package com.voxeo.tropo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class SessionLauncher {

	public TropoLaunchResult launchSession(String baseUrl, String token, Map<String, String> mapParams) {
		
		if (mapParams == null) {
			mapParams = new HashMap<String, String>();
		}
		String url = baseUrl + "sessions?action=create";

		TropoParser parser = new TropoParser();
	    HttpClient client = HttpClientBuilder.create().build();

		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("token", token));
		for (Map.Entry<String, String> entry: mapParams.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
	    
	    BufferedReader reader = null;
		HttpPost method = null;
	    try {
			URIBuilder builder = new URIBuilder(url);

			builder.setParameter("http.useragent", "Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4");
			method = new HttpPost(builder.build());

			method.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4");
			method.setHeader(HttpHeaders.ACCEPT, "application/json");
			method.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
			method.setEntity(entity);
			HttpResponse httpResponse = client.execute(method);
	    	int returnCode = httpResponse.getStatusLine().getStatusCode();
	    	if (returnCode != HttpStatus.SC_OK) {
	    		throw new TropoException(String.format("Server returned with error code: %s", returnCode));
	    	}
	        reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
	        return parser.sessionLaunch(reader);
	    } catch (Exception e) {
	    	throw new TropoException("Could not launch session", e);
	    } finally {
			if (method != null) {
				method.releaseConnection();
			}
	      	if(reader != null) try { reader.close(); } catch (Exception fe) {}
	    }
	}
}
