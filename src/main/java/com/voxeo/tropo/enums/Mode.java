package com.voxeo.tropo.enums;

public enum Mode {
	DTMF, SPEECH, ANY;
	
	@Override public String toString() {
		
		String s = super.toString();
		return s.toLowerCase();
	}
}
