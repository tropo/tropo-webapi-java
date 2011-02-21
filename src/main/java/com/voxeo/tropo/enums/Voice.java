package com.voxeo.tropo.enums;

public enum Voice {
	CARMEN, JORGE, FLORENCE, BERNARD, ALLISON, DAVE, KATE, SIMON, KATRIN, STEFAN, PAOLA, LUCA, SASKIA, WILLEM, SOLEDAD, CARLOS, ZOSIA, KRZYSZTOF;
	
	@Override public String toString() {
		
		String s = super.toString();
		return s.toLowerCase();
	}
}
