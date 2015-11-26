package com.voxeo.tropo;

import java.io.Serializable;

public class TropoLaunchResult implements Serializable {

	private static final long serialVersionUID = 9173320073634939470L;

	private Boolean success;
	private String token;
	private String id;
	
	public Boolean isSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
    public String toString(){
	    return TropoUtils.toPrettyString(this);
    }
}
