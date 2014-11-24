package com.voxeo.tropo;

import com.voxeo.tropo.actions.*;
import com.voxeo.tropo.enums.Channel;
import com.voxeo.tropo.enums.Format;
import com.voxeo.tropo.enums.Network;
import support.ActionSupportHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static com.voxeo.tropo.Key.*;

public class Tropo extends ArrayBackedJsonAction {

  private TropoParser                                parser;
  private String                                     baseUrl                            = "https://api.tropo.com/v1/";

  private ActionSupportHandler<SayAction>            sayActionSupportHandler            = new ActionSupportHandler<SayAction>(SayAction.class);
  private ActionSupportHandler<AskAction>            askActionSupportHandler            = new ActionSupportHandler<AskAction>(AskAction.class);
  private ActionSupportHandler<ConferenceAction>     conferenceActionSupportHandler     = new ActionSupportHandler<ConferenceAction>(ConferenceAction.class);
  private ActionSupportHandler<ChoicesAction>        choicesActionSupportHandler        = new ActionSupportHandler<ChoicesAction>(ChoicesAction.class);
  private ActionSupportHandler<OnAction>             onActionSupportHandler             = new ActionSupportHandler<OnAction>(OnAction.class);
  private ActionSupportHandler<RecordAction>         recordActionSupportHandler         = new ActionSupportHandler<RecordAction>(RecordAction.class);
  private ActionSupportHandler<RedirectAction>       redirectActionSupportHandler       = new ActionSupportHandler<RedirectAction>(RedirectAction.class);
  private ActionSupportHandler<StartRecordingAction> startRecordingActionSupportHandler = new ActionSupportHandler<StartRecordingAction>(StartRecordingAction.class);
  private ActionSupportHandler<TransferAction>       transferActionSupportHandler       = new ActionSupportHandler<TransferAction>(TransferAction.class);
  private ActionSupportHandler<CallAction>           callActionSupportHandler           = new ActionSupportHandler<CallAction>(CallAction.class);
  private ActionSupportHandler<MessageAction>        messageActionSupportHandler        = new ActionSupportHandler<MessageAction>(MessageAction.class);
  private ActionSupportHandler<WaitAction>           waitActionSupportHandler        = new ActionSupportHandler<WaitAction>(WaitAction.class);

  public Tropo() {

    super("tropo");
    parser = new TropoParser();
  }

  public SayAction say(String text) {

    return say(VALUE(text));
  }

  public SayAction say(Key... keys) {

    return sayActionSupportHandler.build(this, "tropo", keys);
  }

  public AskAction ask(Key... keys) {

    return askActionSupportHandler.build(this, "tropo", keys);
  }

  public AskAction ask(String name, Boolean bargein, Float timeout, Boolean required) {

    return ask(NAME(name), BARGEIN(bargein), TIMEOUT(timeout), REQUIRED(required));
  }

  public AskAction ask(String name, Boolean bargein, Float timeout, Float sensitivity, Boolean required) {

    return ask(NAME(name), BARGEIN(bargein), TIMEOUT(timeout), SENSITIVITY(sensitivity), REQUIRED(required));
  }

  public ChoicesAction choices(Key... keys) {

    return choicesActionSupportHandler.build(this, "tropo", keys);
  }

  public ConferenceAction conference(Key... keys) {

    return conferenceActionSupportHandler.build(this, "tropo", keys);
  }

  public OnAction on(Key... keys) {

    return onActionSupportHandler.build(this, "tropo", keys);
  }

  public OnAction on(String event, String next) {

    return on(EVENT(event), NEXT(next));
  }

  public RecordAction record(Key... keys) {

    return recordActionSupportHandler.build(this, "tropo", keys);
  }

  public RecordAction record(String name, String url, Boolean beep, Boolean sendTones, String exitTone) {

    return record(NAME(name), URL(url), BEEP(beep), SEND_TONES(sendTones), EXIT_TONE(exitTone));
  }

  public RedirectAction redirect(Key... keys) {

    return redirectActionSupportHandler.build(this, "tropo", keys);
  }

  public RedirectAction redirect(String to) {

    return redirect(TO(to));
  }

  public RedirectAction redirect(String to, String from) {

    return redirect(TO(to), FROM(from));
  }

  public StartRecordingAction startRecording(Key... keys) {

    return startRecordingActionSupportHandler.build(this, "tropo", keys);
  }

  public StartRecordingAction startRecording(String url) {

    return startRecording(URL(url));
  }

  public StartRecordingAction startRecording(String url, Format format) {

    return startRecording(URL(url), FORMAT(format));
  }

  public StartRecordingAction startRecording(String url, Format format, String username, String password) {

    return startRecording(URL(url), FORMAT(format), USERNAME(username), PASSWORD(password));
  }

  public void hangup(Key... key) {

    addNull("tropo", "hangup");
  }

  public void stopRecording(Key... key) {

    addNull("tropo", "stopRecording");
  }

  public void reject(Key... key) {

    addNull("tropo", "reject");
  }

  public TransferAction transfer(Key... keys) {

    return transferActionSupportHandler.build(this, "tropo", keys);
  }

  public TransferAction transfer(String to) {

    return transfer(TO(to));
  }

  public TransferAction transfer(String to, String from) {

    return transfer(TO(to), FROM(from));
  }

  public CallAction call(Key... keys) {

    return callActionSupportHandler.build(this, "tropo", keys);
  }

  public CallAction call(String to) {

    return call(TO(to));
  }

  public CallAction call(String to, Network network) {

    return call(TO(to), NETWORK(network));
  }

  public CallAction call(String to, Network network, String from) {

    return call(TO(to), NETWORK(network), FROM(from));
  }

  public CallAction call(String to, Network network, String from, Channel channel) {

    return call(TO(to), NETWORK(network), FROM(from), CHANNEL(channel));
  }

  public MessageAction message(Key... keys) {

    return messageActionSupportHandler.build(this, "tropo", keys);
  }

  public MessageAction message(String to) {

    return message(TO(to));
  }

  public MessageAction message(String to, Network network) {

    return message(TO(to), NETWORK(network));
  }

  public MessageAction message(String to, Network network, String from) {

    return message(TO(to), NETWORK(network), FROM(from));
  }

  public MessageAction message(String to, Network network, String from, Channel channel) {

    return message(TO(to), NETWORK(network), FROM(from), CHANNEL(channel));
  }

  public WaitAction serverWait(Long  milliseconds) {

      return serverWait(MILLISECONDS(milliseconds));
  }

  public WaitAction serverWait(Key... keys){
      return waitActionSupportHandler.build(this, "tropo", keys);
  }

  public TropoResult parse(String json) {

    return parser.parse(json);
  }

  public TropoSession session(String json) {

    return parser.session(json);
  }

  public TropoResult parse(HttpServletRequest request) {

    return parser.parse(request);
  }

  public TropoSession session(HttpServletRequest request) {

    return parser.session(request);
  }

  @SuppressWarnings("rawtypes")
  public Map parse(Map json) {

    return parser.parse(json);
  }

  public void render(HttpServletResponse response) {

    render(response, text());
  }

  public void render(HttpServletResponse response, String json) {

    try {
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      response.getWriter().write(text());
      response.getWriter().flush();
      response.getWriter().close();
    } catch (IOException ioe) {
      throw new TropoException("An error happened while rendering response", ioe);
    }
  }

  public boolean isEmpty() {

    return super.isEmpty();
  }

  @SuppressWarnings("unchecked")
  public TropoLaunchResult launchSession(String token) {

    return launchSession(token, Collections.EMPTY_MAP);
  }

  public TropoLaunchResult launchSession(String token, Map<String, String> params) {

    SessionLauncher launcher = new SessionLauncher();
    return launcher.launchSession(baseUrl, token, params);
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }
}
