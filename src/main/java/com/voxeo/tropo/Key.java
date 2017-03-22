package com.voxeo.tropo;

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
