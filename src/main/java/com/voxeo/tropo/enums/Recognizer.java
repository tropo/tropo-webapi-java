package com.voxeo.tropo.enums;

public enum Recognizer {
	GERMAN, BRITISH_ENGLISH, US_ENGLISH, SPANISH, @Deprecated MEXICAN_SPANISH, FRENCH, FRENCH_CANADIAN, ITALIAN, POLISH, DUTCH,
	AFRIKAANS, ARABIC, ARABIC_JORDANIAN, ASSAMESE, BASQUE, BENGALI, BENGALI_INDIAN, BHOJPURI, BULGARIAN, CANTONESE,
	CATALAN, CZECH, DANISH, DUTCH_BELGIAN, ENGLISH_AUSTRALIAN, ENGLISH_INDIAN, ENGLISH_SINGAPOREAN, ENGLISH_SOUTH_AFRICAN,
	ENGLISH_UK, ENGLISH_US, FINNISH, FRENCH_BELGIAN, GALICIAN, GERMAN_AUSTRIAN, GERMAN_SWISS, GREEK, GUJARATI, HEBREW,
	HINDI, HUNGARIAN, ICELANDIC, INDONESIAN, JAPANESE, KANNADA, KOREAN, MALAY, MALAYALAM, MANDARIN, MANDARIN_TAIWANESE,
	MARATHI, NEPALI, NORWEGIAN, ORIYA, PORTUGUESE, PORTUGUESE_BRAZILIAN, PUNJABI, ROMANIAN, RUSSIAN, SERBIAN, SLOVAK,
	SLOVENIAN, SPANISH_ARGENTINIAN, SPANISH_COLOMBIAN, SPANISH_US_MEXICAN, SWEDISH, TAMIL, TELUGU, THAI, TURKISH, UKRAINIAN,
	URDU_INDIAN, URDU_PAKISTANI, VALENCIAN, VIETNAMESE, WELSH;
		
	@Override public String toString() {
		
		switch(this) {
			case GERMAN : return "de-de";
			case BRITISH_ENGLISH : return "en-gb";
			case US_ENGLISH : return "en-us";
			case SPANISH : return "es-es";
			case MEXICAN_SPANISH : return "es-mx";
			case FRENCH : return "fr-fr";
			case FRENCH_CANADIAN : return "fr-ca";
			case ITALIAN : return "it-it";
			case POLISH : return "pl-pl";
			case DUTCH : return "nl-nl";
			case AFRIKAANS : return "af-za";
      case ARABIC : return "ar-ww";
      case ARABIC_JORDANIAN : return "ar-jo";
      case ASSAMESE : return "as-in";
      case BASQUE : return "eu-es";
      case BENGALI : return "bn-bd";
      case BENGALI_INDIAN : return "bn-in";
      case BHOJPURI : return "bh-in";
      case BULGARIAN : return "bg-bg";
      case CANTONESE : return "cn-hk";
      case CATALAN : return "ca-es";
      case CZECH : return "cs-cz";
      case DANISH : return "da-dk";
      case DUTCH_BELGIAN : return "nl-be";
      case ENGLISH_AUSTRALIAN : return "en-au";
      case ENGLISH_INDIAN : return "en-in";
      case ENGLISH_SINGAPOREAN : return "en-sg";
      case ENGLISH_SOUTH_AFRICAN : return "en-za";
      case ENGLISH_UK : return "en-gb";
      case ENGLISH_US : return "en-us";
      case FINNISH : return "fi-fi";
      case FRENCH_BELGIAN : return "fr-be";
      case GALICIAN : return "gl-es";
      case GERMAN_AUSTRIAN : return "de-at";
      case GERMAN_SWISS : return "de-ch";
      case GREEK : return "el-gr";
      case GUJARATI : return "gu-in";
      case HEBREW : return "he-il";
      case HINDI : return "hi-in";
      case HUNGARIAN : return "hu-hu";
      case ICELANDIC : return "is-is";
      case INDONESIAN : return "id-id";
      case JAPANESE : return "ja-jp";
      case KANNADA : return "kn-in";
      case KOREAN : return "ko-kr";
      case MALAY : return "ms-my";
      case MALAYALAM : return "ml-in";
      case MANDARIN : return "zh-cn";
      case MANDARIN_TAIWANESE : return "zh-tw";
      case MARATHI : return "mr-in";
      case NEPALI : return "ne-np";
      case NORWEGIAN : return "no-no";
      case ORIYA : return "or-in";
      case PORTUGUESE : return "pt-pt";
      case PORTUGUESE_BRAZILIAN : return "pt-br";
      case PUNJABI : return "pa-in";
      case ROMANIAN : return "ro-ro";
      case RUSSIAN : return "ru-ru";
      case SERBIAN : return "sr-rs";
      case SLOVAK : return "sk-sk";
      case SLOVENIAN : return "sl-si";
      case SPANISH_ARGENTINIAN : return "es-ar";
      case SPANISH_COLOMBIAN : return "es-co";
      case SPANISH_US_MEXICAN : return "es-us";
      case SWEDISH : return "sv-se";
      case TAMIL : return "ta-in";
      case TELUGU : return "te-in";
      case THAI : return "th-th";
      case TURKISH : return "tr-tr";
      case UKRAINIAN : return "uk-ua";
      case URDU_INDIAN : return "ur-in";
      case URDU_PAKISTANI : return "ur-pk";
      case VALENCIAN : return "va-es";
      case VIETNAMESE : return "vi-vn";
      case WELSH : return "cy-gb";
		}
		return super.toString();
	}
}
