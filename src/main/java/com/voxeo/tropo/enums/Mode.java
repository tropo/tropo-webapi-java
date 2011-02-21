package com.voxeo.tropo.enums;

public enum Mode {
	DTMF, SPEECH;
	
	@Override public String toString() {
		
		String s = super.toString();
		return s.toLowerCase();
	}
}
