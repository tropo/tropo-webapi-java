package com.voxeo.tropo;

import java.util.HashMap;
import java.util.Map;

import com.voxeo.tropo.enums.As;
import com.voxeo.tropo.enums.Channel;
import com.voxeo.tropo.enums.EmailFormat;
import com.voxeo.tropo.enums.Format;
import com.voxeo.tropo.enums.Method;
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

  /**
   * <p>
   * The FTP or HTTP URL or S3 Bucket to send the recorded audio file. When
   * sending via POST, the name of the form field is "filename". Accepts SSL
   * (FTPS and HTTPS) and SFTP URLs as well. If the URL is an S3 bucket, Tropo
   * will upload to that, using the "username" parameter as the Access Key ID,
   * and your "password" parameter as the Access Key Secret.
   * </p>
   * <p>
   * The file will take a few moments to upload to your server. The exact amount
   * of time depends on many factors, including the network connection of your
   * server. If your application needs to play back the audio immediately after
   * recording is completed, the object returned by the record method has a
   * "value" property that contains a url of a temporary local copy of the file.
   * This temporary copy will be deleted as soon as the call ends.
   * </p>
   * <p>
   * Please note this needs to be a fully qualified URL, i.e.
   * "http:​//​example.com/folder/subfolder" vs "/folder/subfolder". If you
   * don't have a complete URL, the file won't send at all or at the very least
   * won't send correctly (0 byte file and similar unusable content).
   * </p>
   * <p>
   * For FTP, include the full URl including the file name, i.e.
   * "ftp:​//example.com/folder/my-recording.wav"
   * </p>
   */
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

  /**
   * This specifies the format for the audio recording; it can be
   * {@link Format#WAV}, {@link Format#MP3} or {@link Format#AU}.
   * <p>
   * Default:{@link Format#WAV}
   * </p>
   */
	public static Key FORMAT(Format value) {

		return createKey("format", value);
	}

  /**
   * <p>
   * Defines the username for uploading your recording. For HTTP, this is a
   * Basic Auth user.
   * </p>
   * <p>
   * For FTP, this is your FTP server's user name.
   * </p>
   * <p>
   * For S3, this is your AWS Access Key ID.
   * </p>
   * <p>
   * Note: If user name contains @ or /, the character must be URL encoded.
   * </p>
   */
	public static Key USERNAME(String value) {

		return createKey("username", value);
	}

  /**
   * <p>
   * Defines the password for uploading a file. When using HTTP, this will be
   * used as a password for Basic Auth to your server.
   * </p>
   * <p>
   * For FTP, this is your FTP user password.
   * </p>
   * <p>
   * For S3, this is your AWS access key secret.
   * </p>
   * <p>
   * Note: If password contains @ or /, the character must be URL encoded.
   * </p>
   */
	public static Key PASSWORD(String value) {

		return createKey("password", value);
	}

  /**
   * @deprecated The preferred way to do this is via the {@link #METHOD(Method)}
   */
	@Deprecated
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
    
  /**
   * <p>
   * Setting to true will instruct Tropo to upload the recording file in the
   * background as soon as the recording is completed.
   * </p>
   * <p>
   * If this is set to false (the default behavior), Tropo will wait until the
   * file is uploaded before returning or running the onRecord callback.
   * </p>
   * <p>
   * Default: false
   * </p>
   */
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

  /**
   * For HTTP recording upload, this parameter determines the method used. This
   * can be {@link Method#POST} (which is the default) or {@link Method#PUT}.
   * When sending via {@link Method#POST}, the file is sent as if you uploaded
   * in a web form with a form field name of "filename".
   */
    public static Key METHOD(Method method) {

      return createKey("method", method);
    }

  /**
   * <p>
   * Setting this to anything enables transcription on this recording. The
   * e-mail address or HTTP URL to send the transcription results to; the
   * transcription arrives as the content of the HTTP POST, as opposed to a
   * header, named field or variable, and is not sent as form data.
   * </p>
   * <p>
   * Email addresses must be prefaced with mailto: if used
   * (mailto:you@example.com)
   * </p>
   */
    public static Key TRANSCRIPTION_OUT_URI(String value) {

    	return createKey("transcriptionOutURI", value);
    }
    
  /**
   * Specifies the encoding used when delivering transcriptions via e-mail.
   * Values can be {@link EmailFormat#PLAIN} or {@link EmailFormat#ENCODED}.
   * <p>
   * {@link EmailFormat#PLAIN}
   * </p>
   */
    public static Key TRANSCRIPTION_EMAIL_FORMAT(EmailFormat emailFormat) {

    	return createKey("transcriptionEmailFormat", emailFormat);
    }
    
  /**
   * User definable ID that can be included when the transcription is posted to
   * transcriptionOutURI.
   */
    public static Key TRANSCRIPTION_ID(String value) {

    	return createKey("transcriptionID", value);
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
