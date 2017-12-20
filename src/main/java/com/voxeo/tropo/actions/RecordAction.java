package com.voxeo.tropo.actions;

import static com.voxeo.tropo.Key.EMAIL_FORMAT;
import static com.voxeo.tropo.Key.ID;
import static com.voxeo.tropo.Key.URL;
import static com.voxeo.tropo.Key.VALUE;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.TropoException;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;
import com.voxeo.tropo.enums.EmailFormat;

import support.ActionSupportHandler;

@ValidKeys(keys={"name","attempts","allowSignals","bargein","beep","choices","format","maxSilence","maxTime","method",
        "required","transcription","url","password","username","timeout","voice","interdigitTimeout", "asyncUpload","say","promptLogSecurity","sensitivity"})
@RequiredKeys(keys={"url","name"})
public class RecordAction extends JsonAction {
	
	private ActionSupportHandler<SayAction> sayActionSupportHandler = new ActionSupportHandler<SayAction>(SayAction.class);	
	private ActionSupportHandler<ChoicesAction> choicesActionSupportHandler = new ActionSupportHandler<ChoicesAction>(ChoicesAction.class);	
	private ActionSupportHandler<TranscriptionAction> transcriptionActionSupportHandler = new ActionSupportHandler<TranscriptionAction>(TranscriptionAction.class);	

	public RecordAction() {
		
		super();
		setName("record");
	}
	
	public RecordAction(Key... keys) {
	
		super(keys);
		setName("record");
	}	
	
	public SayAction say(String text) {

		return say(VALUE(text));
	}
	
	public SayAction say(Key... keys) {

		return sayActionSupportHandler.build(this, keys);
	}	
	
	public ChoicesAction choices(Key... keys) {

		return choicesActionSupportHandler.build(this, keys);
	}
	
	public ChoicesAction choices(String value) {

		return choices(VALUE(value));
	}

	public TranscriptionAction transcription(Key... keys) {

		return transcriptionActionSupportHandler.build(this, keys);
	}	
	
	public TranscriptionAction transcription(String id, String url, String emailFormat, String language) {

		return transcription(ID(id), URL(url), EMAIL_FORMAT(emailFormat), Key.LANGUAGE(language));
	}
	
  public static class Say {
    private String value;

    private String event;

    public Say(String value) {
      if (value == null || value.trim().equals(""))
        throw new TropoException("Missing required property: value of record.say");
      this.value = value;
    }

    public Say(String value, String event) {
      if (value == null || value.trim().equals(""))
        throw new TropoException("Missing required property: value of record.say");
      if (!("timeout".equals(event)))
        throw new TropoException("For record, the only possible event is 'timeout'.");
      this.value = value;
      this.event = "timeout";
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public String getEvent() {
      return event;
    }

    public void setEvent(String event) {
      this.event = event;
    }
  }
  
  public static class Transcription {
    private String id;

    private String url;

    private EmailFormat emailFormat;

    public Transcription(String url) {
      super();
      this.url = url;
    }

    public Transcription(String id, String url) {
      super();
      this.id = id;
      this.url = url;
    }

    public Transcription(String id, String url, EmailFormat emailFormat) {
      super();
      this.id = id;
      this.url = url;
      this.emailFormat = emailFormat;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public EmailFormat getEmailFormat() {
      return emailFormat;
    }

    public void setEmailFormat(EmailFormat emailFormat) {
      this.emailFormat = emailFormat;
    }
  }

  public static class Url {
    private String url;

    private String username;

    private String password;

    private String method;

    public Url(String url) {
      super();
      this.url = url;
    }

    public Url(String url, String username, String password, String method) {
      super();
      if (url == null || url.trim().equals("")) {
        throw new TropoException("Missing required property: url of Url");
      }
      this.url = url;
      this.username = username;
      this.password = password;
      this.method = method;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getMethod() {
      return method;
    }

    public void setMethod(String method) {
      this.method = method;
    }
  }
	
	@Override
	protected void validate() throws TropoException {
		
		super.validate();
		checkUrl("url");
	}
}
