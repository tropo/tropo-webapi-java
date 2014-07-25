package com.voxeo.tropo.actions;

import support.ActionSupportHandler;
import support.HeadersSupportHandler;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys = { "to", "allowSignals", "answerOnMedia", "channel", "from", "headers", "name", "network", "recording", "required", "timeout" })
@RequiredKeys(keys = { "to" })
public class CallAction extends JsonAction {

  private ActionSupportHandler<StartRecordingAction> startRecordingActionSupportHandler = new ActionSupportHandler<StartRecordingAction>(StartRecordingAction.class);
  private HeadersSupportHandler                      headersSupportHandler              = new HeadersSupportHandler();

  public CallAction() {

    super();
    setName("call");
  }

  public CallAction(Key... keys) {

    super(keys);
    setName("call");
  }

  public StartRecordingAction startRecording(Key... keys) {

    return startRecordingActionSupportHandler.build(this, keys);
  }

  public HeadersAction headers(String[]... keys) {

    return headersSupportHandler.headers(this, keys);
  }
}
