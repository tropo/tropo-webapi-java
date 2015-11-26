package com.voxeo.tropo;

import java.io.Serializable;

public class TropoEntity implements Serializable {

	private static final long serialVersionUID = -7724357281250549712L;
	
	private String id;
	private String name;
	private String channel;
	private String network;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	
	@Override
    public String toString(){
	    return TropoUtils.toPrettyString(this);
    }
}
