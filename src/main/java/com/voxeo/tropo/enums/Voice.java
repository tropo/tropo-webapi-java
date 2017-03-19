package com.voxeo.tropo.enums;

public enum Voice {
	@Deprecated CARMEN,JORGE, @Deprecated FLORENCE, @Deprecated BERNARD, ALLISON, @Deprecated DAVE, KATE, @Deprecated SIMON, @Deprecated KATRIN, @Deprecated STEFAN, PAOLA, LUCA, @Deprecated SASKIA, @Deprecated WILLEM, SOLEDAD, CARLOS, ZOSIA, KRZYSZTOF,
	LAILA, MAGED, TARIK, DAMAYANTI, MIREN, DARIA, SINJI, MONTSERRAT, JORDI, IVETA, ZUZANA, SARA, MAGNUS, CLAIRE, XANDER, ELLEN, KAREN, LEE, VEENA,
	MOIRA, FIONA, TESSA, SERENA, DANIEL, OLIVER, AVA, SAMANTHA, TOM, VICTOR, SATU, ONNI, AUDREY, AURELIE, THOMAS, AMELIE, CHANTAL, NICOLAS, CARMELA,
	ANNA, PETRA, MARKUS, YANNICK, MELINA, NIKOS, CARMIT, LEKHA, MARISKA, ALICE, FEDERICA, KYOKO, OTOYA, SORA, TIANTIAN, MEIJIA, NORA, HENRIK, EWA,
	CATARINA, JOANA, JOAQUIM, LUCIANA, FELIPE, KATYA, MILENA, YURI, LAURA, DIEGO, MONICA, ANGELICA, PAULINA, JUAN, ALVA, KLARA, OSKAR, KANYA, YELDA,
	CEM, EMPAR;
	
	@Override public String toString() {
		
	  switch(this) {
	    case SINJI:
	      return "sin-ji";
	    case TIANTIAN:
	      return "tian-tian";
	    case MEIJIA:
	      return "mei-jia";
	    default:
	      break;
	  }

		String s = super.toString();
		return s.toLowerCase();
	}
}
