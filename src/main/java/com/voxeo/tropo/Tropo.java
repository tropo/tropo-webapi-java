package com.voxeo.tropo;

import static com.voxeo.tropo.Key.BARGEIN;
import static com.voxeo.tropo.Key.BEEP;
import static com.voxeo.tropo.Key.CHANNEL;
import static com.voxeo.tropo.Key.EVENT;
import static com.voxeo.tropo.Key.EXIT_TONE;
import static com.voxeo.tropo.Key.FORMAT;
import static com.voxeo.tropo.Key.FROM;
import static com.voxeo.tropo.Key.MILLISECONDS;
import static com.voxeo.tropo.Key.NAME;
import static com.voxeo.tropo.Key.NETWORK;
import static com.voxeo.tropo.Key.NEXT;
import static com.voxeo.tropo.Key.PASSWORD;
import static com.voxeo.tropo.Key.REQUIRED;
import static com.voxeo.tropo.Key.SEND_TONES;
import static com.voxeo.tropo.Key.SENSITIVITY;
import static com.voxeo.tropo.Key.TIMEOUT;
import static com.voxeo.tropo.Key.TO;
import static com.voxeo.tropo.Key.URL;
import static com.voxeo.tropo.Key.USERNAME;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.HttpClient;

import com.voxeo.tropo.actions.ArrayBackedJsonAction;
import com.voxeo.tropo.actions.AskAction;
import com.voxeo.tropo.actions.CallAction;
import com.voxeo.tropo.actions.ChoicesAction;
import com.voxeo.tropo.actions.ConferenceAction;
import com.voxeo.tropo.actions.MessageAction;
import com.voxeo.tropo.actions.OnAction;
import com.voxeo.tropo.actions.RecordAction;
import com.voxeo.tropo.actions.RedirectAction;
import com.voxeo.tropo.actions.SayAction;
import com.voxeo.tropo.actions.StartRecordingAction;
import com.voxeo.tropo.actions.TransferAction;
import com.voxeo.tropo.actions.WaitAction;
import com.voxeo.tropo.enums.Channel;
import com.voxeo.tropo.enums.Format;
import com.voxeo.tropo.enums.LogSecurityState;
import com.voxeo.tropo.enums.Network;

import support.ActionSupportHandler;

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

  public SayAction say(String text, String name) {

    return say(Key.VALUE(text), Key.NAME(name));
  }

  public SayAction say(Key... keys) {

    return sayActionSupportHandler.build(this, "tropo", keys);
  }

  public AskAction ask(Key... keys) {

    return askActionSupportHandler.build(this, "tropo", keys);
  }

  /**
   * @deprecated A choices and a say are required.
   * @see Tropo#ask(Key...)
   * @since 0.4.6
   */
  @Deprecated
  public AskAction ask(String name, Boolean bargein, Float timeout, Boolean required) {

    return ask(NAME(name), BARGEIN(bargein), TIMEOUT(timeout), REQUIRED(required));
  }

  /**
   * @deprecated A choices and a say are required.
   * @see Tropo#ask(Key...)
   * @since 0.4.6
   */
  @Deprecated
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

  /**
   * @deprecated A say is required.
   * @see Tropo#on(Key...)
   * @since 0.4.8
   */
  @Deprecated
  public OnAction on(String event, String next) {

    return on(EVENT(event), NEXT(next));
  }

  public RecordAction record(Key... keys) {

    return recordActionSupportHandler.build(this, "tropo", keys);
  }

  /**
   * @deprecated A sendTones and a exitTone are invalid.
   * @see Tropo#record(String, String, Boolean)
   * @since 0.4.8
   */
  @Deprecated
  public RecordAction record(String name, String url, Boolean beep, Boolean sendTones, String exitTone) {

    return record(NAME(name), URL(url), BEEP(beep), SEND_TONES(sendTones), EXIT_TONE(exitTone));
  }

  public RecordAction record(String name, String url, Boolean beep) {

    return record(NAME(name), URL(url), BEEP(beep));
  }

  public RedirectAction redirect(Key... keys) {

    return redirectActionSupportHandler.build(this, "tropo", keys);
  }

  /**
   * @deprecated A name is invalid.
   * @see Tropo#redirect(Key...)
   * @since 0.4.8
   */
  @Deprecated
  public RedirectAction redirect(String to) {

    return redirect(TO(to));
  }

  /**
   * @deprecated A name is invalid.
   * @see Tropo#redirect(Key...)
   * @since 0.4.8
   */
  @Deprecated
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

  public TransferAction transfer(String to, String name) {

    return transfer(Key.TO(to), Key.NAME(name));
  }

  public TransferAction transfer(String to, String from, String name) {

    return transfer(Key.TO(to), Key.FROM(from), Key.NAME(name));
  }

  public CallAction call(Key... keys) {

    return callActionSupportHandler.build(this, "tropo", keys);
  }

  public CallAction call(String name, String to) {

    return call(NAME(name), TO(to));
  }

  public CallAction call(String name, String to, Network network) {

    return call(NAME(name), TO(to), NETWORK(network));
  }

  public CallAction call(String name, String to, Network network, String from) {

    return call(NAME(name), TO(to), NETWORK(network), FROM(from));
  }

  public CallAction call(String name, String to, Network network, String from, Channel channel) {

    return call(NAME(name), TO(to), NETWORK(network), FROM(from), CHANNEL(channel));
  }

  public MessageAction message(Key... keys) {

    return messageActionSupportHandler.build(this, "tropo", keys);
  }

  /**
   * @deprecated A say and a name are required.
   * @see Tropo#message(Key...)
   * @since 0.4.7
   */
  @Deprecated
  public MessageAction message(String to) {

    return message(TO(to));
  }

  /**
   * @deprecated A say and a name are required.
   * @see Tropo#message(Key...)
   * @since 0.4.7
   */
  @Deprecated
  public MessageAction message(String to, Network network) {

    return message(TO(to), NETWORK(network));
  }

  /**
   * @deprecated A say and a name are required.
   * @see Tropo#message(Key...)
   * @since 0.4.7
   */
  @Deprecated
  public MessageAction message(String to, Network network, String from) {

    return message(TO(to), NETWORK(network), FROM(from));
  }

  /**
   * @deprecated A say and a name are required.
   * @see Tropo#message(Key...)
   * @since 0.4.7
   */
  @Deprecated
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

  public void render(HttpServletResponse response) {

    render(response, text());
  }

  public void render(HttpServletResponse response, String json) {

    try {
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.addHeader("WebAPI-Lang-Ver", "Java V15.7.0 2017-05-12");

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

  public TropoLaunchResult launchSession(String token, Map<String, String> params, HttpClient httpClient) {
    SessionLauncher launcher = new SessionLauncher(httpClient);
    return launcher.launchSession(baseUrl, token, params);
  }

  public void generalLogSecurity(LogSecurityState value) {

    addObject("tropo", "generalLogSecurity", value.toString());
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }
}
