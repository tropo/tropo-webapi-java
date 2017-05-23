package com.voxeo.tropo.actions;

import support.HeadersSupportHandler;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys = {  "timeout", "name", "label", "allowSignals", "headers" })
// @RequiredKeys(keys = { "name" })
public class AnswerAction extends JsonAction {

  private HeadersSupportHandler                      headersSupportHandler              = new HeadersSupportHandler();

  public AnswerAction() {

    super();
    setName("answer");
  }

  public AnswerAction(Key... keys) {

    super(keys);
    setName("answer");
  }


  public HeadersAction headers(String[]... keys) {

    return headersSupportHandler.headers(this, keys);
  }
}
