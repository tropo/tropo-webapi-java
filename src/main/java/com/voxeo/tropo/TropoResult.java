package com.voxeo.tropo;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class TropoResult implements Serializable {

	private static final long serialVersionUID = 7944603295887403063L;

	private ArrayList<ActionResult> actions = new ArrayList<ActionResult>();
	
	private Boolean complete;
	private String error;
	private Integer sequence;
	private Integer sessionDuration;
	private String sessionId;
	private String state;
	private String callId;
	
	@SerializedName("calledid")
	private String calledId;

	
	public ArrayList<ActionResult> getActions() {
		return actions;
	}
	public void setActions(ArrayList<ActionResult> actions) {
		this.actions = actions;
	}
	public Boolean isComplete() {
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
    
    public String getCallId() {
        return callId;
    }
    public void setCallId(String callId) {
        this.callId = callId;
    }
    public String getCalledId() {
        return calledId;
    }
    public void setCalledId(String calledid) {
        this.calledId = calledid;
    }	
    
    @Override
    public String toString(){
        return TropoUtils.toPrettyString(this);
    }
}
