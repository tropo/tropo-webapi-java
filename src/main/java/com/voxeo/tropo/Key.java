package com.voxeo.tropo;

import java.util.HashMap;
import java.util.Map;

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

  /**
   * This determines whether Tropo should move on to the next verb; if required
   * is set to 'true', Tropo will only move on to the next verb if the current
   * operation completely successfully.
   * <p>
   * Default: true
   * </p>
   */
	public static Key REQUIRED(Boolean value) {

		return createKey("required", value);
	}

	public static Key BARGEIN(Boolean value) {

		return createKey("bargein", value);
	}

	public static Key TIMEOUT(Float value) {

		return createKey("timeout", value);
	}

  /**
   * This is useful to allow to help users restart the process if they mistyped.
   * 
   * @deprecated The preferred way to do this is via the
   *             {@link #INTERDIGIT_TIMEOUT(Float)}
   * @param value
   *          How long does Tropo wait between key presses to determine the user
   *          is done with their input.
   */
	@Deprecated
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

  /**
   * Adds the user to the conference room with their audio muted.
   * <p>
   * Default: false
   * </p>
   */
	public static Key MUTE(Boolean value) {

		return createKey("mute", value);
	}

  /**
   * This is the touch-tone key used to exit the conference.
   * 
   * @deprecated The preferred way to do this is via the
   *             {@link #TERMINATOR(String)}
   */
	@Deprecated
	public static Key EXIT_TONE(String value) {

		return createKey("exit_tone", value);
	}

  /**
   * This defines whether to send touch tone phone input to the conference or
   * block the audio.
   * 
   * @deprecated The preferred way to do this is via the
   *             {@link #PLAY_TONES(Boolean)}
   */
	@Deprecated
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

  /**
   * Define allowSignals as an empty string (""), it defines the function as
   * "uninterruptible".
   */
	public static Key ALLOW_SIGNALS() {

		return createKey("allowSignals", "");
	}

  /**
   * <p>
   * It allows you to assign a signal to this function. Events from the Tropo
   * REST API with a matching signal name will "interrupt" the function (i.e.,
   * stop it from running). If it already ran and completed, your interrupt
   * request will be ignored. If the function has not run yet, the interrupt
   * will be queued until it does run.
   * </p>
   * <p>
   * By default, allowSignals will accept any signal as valid; you can also use
   * an array - the function will stop if it receives an interrupt signal
   * matching any of the names in the array.
   * </P>
   */
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

  /**
   * This is the touch-tone key (also known as "DTMF digit") used to exit.
   */
    public static Key TERMINATOR(String value) {

        return createKey("terminator", value);
    }
    
    public static Key MAX_TIME(Float value) {

        return createKey("maxTime", value);
    }
    
    public static Key ASYNC_UPLOAD(Boolean value) {

        return createKey("asyncUpload", value);
    }

  /**
   * How long does Tropo wait between key presses to determine the user is done
   * with their input. This is useful to allow to help users restart the process
   * if they mistyped.
   * <p>
   * Default: 5.0
   * </p>
   */
    public static Key INTERDIGIT_TIMEOUT(Float value) {

      return createKey("interdigitTimeout", value);
    }

  /**
   * Defines a prompt that plays to all participants of a conference when
   * someone joins the conference. If set to true, the default beep is played.
   * <p>
   * Default: false
   * </p>
   */
    public static Key JOIN_PROMPT(Boolean value) {

      return createKey("joinPrompt", value);
    }

  /**
   * Defines a prompt that plays to all participants of a conference when
   * someone joins the conference.It's possible to define either TTS or an audio
   * URL using additional attributes value and voice
   * 
   * @param value
   *          value is used to play simple TTS and/or an audio URL, and supports
   *          SSML
   * @param voice
   *          voice is used to define which of the available voices should be
   *          used for TTS; if voice is left undefined, the default voice will
   *          be used
   * @return
   */
    public static Key JOIN_PROMPT(String value, Voice voice) {

      Map<String, String> map = new HashMap<String, String>();
      map.put("value", value);
      if (voice != null) {
        map.put("voice", voice.toString());
      }

      return createKey("joinPrompt", map);
    }

    public static Key JOIN_PROMPT(String value) {

      return JOIN_PROMPT(value, null);
    }

  /**
   * Defines a prompt that plays to all participants of a conference when
   * someone leaves the conference. If set to true, the default beep is played.
   * <p>
   * Default: false
   * </p>
   */
    public static Key LEAVE_PROMPT(Boolean value) {

      return createKey("leavePrompt", value);
    }

  /**
   * Defines a prompt that plays to all participants of a conference when
   * someone leaves the conference.It's possible to define either TTS or an
   * audio URL using additional attributes value and voice
   * 
   * @param value
   *          value is used to play simple TTS and/or an audio URL, and supports
   *          SSML
   * @param voice
   *          voice is used to define which of the available voices should be
   *          used for TTS; if voice is left undefined, the default voice will
   *          be used
   * @return
   */
    public static Key LEAVE_PROMPT(String value, Voice voice) {

      Map<String, String> map = new HashMap<String, String>();
      map.put("value", value);
      if (voice != null) {
        map.put("voice", voice.toString());
      }

      return createKey("leavePrompt", map);
    }

    public static Key LEAVE_PROMPT(String value) {

      return LEAVE_PROMPT(value, null);
    }

  /**
   * This defines whether to send touch tone phone input to the conference or
   * block the audio.
   * <p>
   * Default: false
   * </p>
   */
    public static Key PLAY_TONES(Boolean value) {

      return createKey("playTones", value);
    }

  /**
   * Controls whether Tropo logs the text to speech string used by the method,
   * which disables output logging for this method.
   */
    public static Key PROMPT_LOG_SECURITY() {
      
      return createKey("promptLogSecurity", "suppress");
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
