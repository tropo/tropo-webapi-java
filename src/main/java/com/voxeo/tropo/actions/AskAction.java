package com.voxeo.tropo.actions;

import static com.voxeo.tropo.Key.EVENT;
import static com.voxeo.tropo.Key.NEXT;
import static com.voxeo.tropo.Key.VALUE;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.TropoException;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;
import com.voxeo.tropo.enums.As;
import com.voxeo.tropo.enums.Mode;
import com.voxeo.tropo.enums.Voice;

import support.ActionSupportHandler;

@ValidKeys(keys = { "name", "required", "choices", "allowSignals", "attempts", "bargein", "minConfidence", "recognizer", "timeout", "sensitivity", "voice",
    "interdigitTimeout", "say", "speechCompleteTimeout", "speechIncompleteTimeout", "promptLogSecurity", "asrLogSecurity", "maskTemplate" })
@RequiredKeys(keys = { "name", "choices", "say" })
public class AskAction extends JsonAction {

  private ActionSupportHandler<SayAction>      sayActionSupportHandler     = new ActionSupportHandler<SayAction>(SayAction.class);
  private ActionSupportHandler<ChoicesAction>  choicesActionSupportHandler = new ActionSupportHandler<ChoicesAction>(ChoicesAction.class);
  private ActionSupportHandler<NestedOnAction> onActionSupportHandler      = new ActionSupportHandler<NestedOnAction>(NestedOnAction.class);

  public AskAction() {

    super();
    setName("ask");
  }

  public AskAction(Key... keys) {

    super(keys);
    setName("ask");
  }

  public SayAction say(String text) {

    return say(VALUE(text));
  }

  public SayAction say(Key... keys) {

    return sayActionSupportHandler.build(this, keys);
  }

  public NestedOnAction on(Key... keys) {

    return onActionSupportHandler.build(this, keys);
  }

  public NestedOnAction on(String event, String next) {

    return on(EVENT(event), NEXT(next));
  }

  public ChoicesAction choices(Key... keys) {

    return choicesActionSupportHandler.build(this, keys);
  }

  public AskAction choices(String choice) {

    choices(VALUE(choice));
    return this;
  }

  public static class Choices {

    public Choices(String value) {
      this(value, null, null);
    }

    public Choices(String value, Mode mode) {
      this(value, mode, null);
    }

    public Choices(String value, Mode mode, String terminator) {
      if (value == null || value.trim().equals("")) {
        throw new TropoException("Missing required property: value of Choices");
      } else {
        this.value = value;
      }

      this.mode = mode;

      if (!(terminator == null || terminator.trim().equals(""))) {
        this.terminator = terminator;
      }
    }

    private String value;

    private Mode mode;

    private String terminator;

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public Mode getMode() {
      return mode;
    }

    public void setMode(Mode mode) {
      this.mode = mode;
    }

    public String getTerminator() {
      return terminator;
    }

    public void setTerminator(String terminator) {
      this.terminator = terminator;
    }
  }

  public static class Say {

    public Say(String value) {
      this(value, null, null);
    }

    private Say(String value, Voice voice) {
      this(value, voice, null);
    }

    public Say(String value, String event) {
      this(value, null, event);
    }

    private Say(String value, Voice voice, String event) {
      this(value, null, null, null, null, voice, null, event);
    }

    private Say(String value, String[] allowSignals, As as, String name, Boolean required, Voice voice,
        String promptLogSecurity, String event) {
      if (value == null || value.trim().equals("")) {
        throw new TropoException("Missing required property: value of ask.say");
      } else {
        this.value = value;
      }
      this.allowSignals = allowSignals;
      this.as = as;
      this.name = name;
      this.required = required;
      this.voice = voice;
      if (promptLogSecurity != null && promptLogSecurity.trim().equalsIgnoreCase("suppress")) {
        this.promptLogSecurity = "suppress";
      }
      if (!(event == null || event.trim().equals(""))) {
        this.event = event;
      }
    }

    private String value;

    private String[] allowSignals;

    private As as;

    private String name;

    private Boolean required;

    private Voice voice;

    private String promptLogSecurity;
    
    private String event;

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public String[] getAllowSignals() {
      return allowSignals;
    }

    public void setAllowSignals(String[] allowSignals) {
      this.allowSignals = allowSignals;
    }

    public As getAs() {
      return as;
    }

    public void setAs(As as) {
      this.as = as;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Boolean getRequired() {
      return required;
    }

    public void setRequired(Boolean required) {
      this.required = required;
    }

    public Voice getVoice() {
      return voice;
    }

    public void setVoice(Voice voice) {
      this.voice = voice;
    }

    public String getPromptLogSecurity() {
      return promptLogSecurity;
    }

    public void setPromptLogSecurity(String promptLogSecurity) {
      this.promptLogSecurity = promptLogSecurity;
    }

    public String getEvent() {
      return event;
    }

    public void setEvent(String event) {
      this.event = event;
    }
  }
}
