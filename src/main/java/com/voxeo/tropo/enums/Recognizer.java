package com.voxeo.tropo.enums;

public enum Recognizer {
	GERMAN, BRITISH_ENGLISH, US_ENGLISH, SPANISH, MEXICAN_SPANISH, FRENCH, FRENCH_CANADIAN, ITALIAN, POLISH, DUTCH;
		
	@Override public String toString() {
		
		switch(this) {
			case GERMAN : return "de-de";
			case BRITISH_ENGLISH : return "en-gb";
			case US_ENGLISH : return "en-us";
			case SPANISH : return "es-es";
			case MEXICAN_SPANISH : return "es-mx";
			case FRENCH : return "fr";
			case FRENCH_CANADIAN : return "fr-ca";
			case ITALIAN : return "it-it";
			case POLISH : return "pl-pl";
			case DUTCH : return "nl-nl";
		}
		return super.toString();
	}
}
