package com.voxeo.tropo;

import com.voxeo.tropo.enums.As;
import com.voxeo.tropo.enums.Channel;
import com.voxeo.tropo.enums.Format;
import com.voxeo.tropo.enums.Mode;
import com.voxeo.tropo.enums.Network;
import com.voxeo.tropo.enums.Recognizer;
import com.voxeo.tropo.enums.Voice;

public class Key {

	private String name;
	private Object value;

	private Key(String name) {

		this.name = name;
	}

	public static Key NAME(String value) {

		return createKey("name", value);
	}

	public static Key VALUE(String value) {

		return createKey("value", value);
	}

	public static Key REQUIRED(Boolean value) {

		return createKey("required", value);
	}

	public static Key BARGEIN(Boolean value) {

		return createKey("bargein", value);
	}

	public static Key TIMEOUT(Float value) {

		return createKey("timeout", value);
	}

	public static Key INTERDIGIT_TIMEOUT(Integer value) {

		return createKey("interdigitTimeout", value);
	}

	public static Key CHOICES(String value) {

		return createKey("choices", value);
	}

	public static Key EVENT(String value) {

		return createKey("event", value);
	}

	public static Key NEXT(String value) {

		return createKey("next", value);
	}

	public static Key MODE(Mode value) {

		return createKey("mode", value);
	}

	public static Key ID(String value) {

		return createKey("id", value);
	}

	public static Key MUTE(Boolean value) {

		return createKey("mute", value);
	}
	public static Key EXIT_TONE(String value) {

		return createKey("exit_tone", value);
	}
	public static Key SEND_TONES(Boolean value) {

		return createKey("send_tones", value);
	}

	public static Key SENSITIVITY(Float value) {

		return createKey("sensitivity", value);
	}
	public static Key URL(String value) {

		return createKey("url", value);
	}
	public static Key BEEP(Boolean value) {

		return createKey("beep", value);
	}
	public static Key TO(String value) {

		return createKey("to", value);
	}
	public static Key TO(String... value) {

		return createKey("to", value);
	}
	public static Key FROM(String value) {

		return createKey("from", value);
	}
	public static Key NETWORK(Network value) {

		return createKey("network", value);
	}
	public static Key CHANNEL(Channel value) {

		return createKey("channel", value);
	}
	public static Key ANSWER_ON_MEDIA(Boolean value) {

		return createKey("answerOnMedia", value);
	}
	public static Key FORMAT(Format value) {

		return createKey("format", value);
	}
	public static Key USERNAME(String value) {

		return createKey("username", value);
	}
	public static Key PASSWORD(String value) {

		return createKey("password", value);
	}
	public static Key METHOD(String value) {

		return createKey("method", value);
	}
	public static Key EMAIL_FORMAT(String value) {

		return createKey("emailFormat", value);
	}
	public static Key AS(As as) {

		return createKey("as", as);
	}
	public static Key VOICE(Voice voice) {

		return createKey("voice", voice);
	}
	public static Key ALLOW_SIGNALS() {

		return createKey("allowSignals", "");
	}
	public static Key ALLOW_SIGNALS(String... signals) {

		return createKey("allowSignals", signals);
	}
	public static Key ATTEMPTS(Integer attempts) {

		return createKey("attempts", attempts);
	}
	public static Key MAX_SILENCE(Float value) {

		return createKey("maxSilence", value);
	}
	public static Key MIN_CONFIDENCE(Integer minConfidence) {

		return createKey("minConfidence", minConfidence);
	}
	public static Key RECOGNIZER(Recognizer recognizer) {

		return createKey("recognizer", recognizer);
	}
    public static Key MILLISECONDS(Long milliseconds) {

        return createKey("milliseconds", milliseconds);
    }

    public static Key TERMINATOR(String value) {

        return createKey("terminator", value);
    }
    
    public static Key MAX_TIME(Float value) {

        return createKey("maxTime", value);
    }
    
    public static Key ASYNC_UPLOAD(Boolean value) {

        return createKey("asyncUpload", value);
    }
    
	public static Key createKey(String name, Object value) {

		if (value instanceof Enum) {
			value = value.toString();
		}
		Key key = new Key(name);
		key.value = value;
		return key;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
}
