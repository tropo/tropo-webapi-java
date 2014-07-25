package com.voxeo.tropo.actions;

import static com.voxeo.tropo.Key.EVENT;
import static com.voxeo.tropo.Key.NEXT;
import static com.voxeo.tropo.Key.VALUE;
import support.ActionSupportHandler;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys = { "name", "text", "mode", "required", "choices", "allowSignals", "attempts", "bargein", "minConfidence", "recognizer", "terminator", "timeout", "sensitivity", "voice",
    "interdigitTimeout" })
@RequiredKeys(keys = { "name" })
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
}
