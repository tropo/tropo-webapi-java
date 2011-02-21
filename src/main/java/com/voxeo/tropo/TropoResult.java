package com.voxeo.tropo;

import java.io.Serializable;
import java.util.ArrayList;

public class TropoResult implements Serializable {

	private static final long serialVersionUID = 7944603295887403063L;

	private ArrayList<ActionResult> actions = new ArrayList<ActionResult>();
	
	private Boolean complete;
	private String error;
	private Integer sequence;
	private Integer sessionDuration;
	private String sessionId;
	private String callState;

	
	public ArrayList<ActionResult> getActions() {
		return actions;
	}
	public void setActions(ArrayList<ActionResult> actions) {
		this.actions = actions;
	}
	public Boolean getComplete() {
		return complete;
	}
	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Integer getSessionDuration() {
		return sessionDuration;
	}
	public void setSessionDuration(Integer sessionDuration) {
		this.sessionDuration = sessionDuration;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getCallState() {
		return callState;
	}
	public void setCallState(String state) {
		this.callState = state;
	}	
}
