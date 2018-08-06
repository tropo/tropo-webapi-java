package com.voxeo.tropo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class TropoSession implements Serializable {

	private static final long serialVersionUID = -4017185052524417287L;
	
	public String accountId;
	public String callId;
	public String id;
	public String initialText;
	public String timestamp;
	public String userType;
	private TropoEntity to;
	private TropoEntity from;
	
	private HashMap<String, String> headers;
	private HashMap<String, String> parameters;

	private String subject;

	private List<InitialMedia> initialMedia;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInitialText() {
		return initialText;
	}
	public void setInitialText(String initialText) {
		this.initialText = initialText;
	}
	public HashMap<String, String> getParameters() {
		return parameters;
	}
	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public TropoEntity getTo() {
		return to;
	}
	public void setTo(TropoEntity to) {
		this.to = to;
	}
	public HashMap<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}
	public TropoEntity getFrom() {
		return from;
	}
	public void setFrom(TropoEntity from) {
		this.from = from;
	}
	
	public String getSubject() {
    return subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }
  public List<InitialMedia> getInitialMedia() {
    return initialMedia;
  }
  public void setInitialMedia(List<InitialMedia> initialMedia) {
    this.initialMedia = initialMedia;
  }
  @Override
    public String toString(){
	    return TropoUtils.toPrettyString(this);
    }
}
