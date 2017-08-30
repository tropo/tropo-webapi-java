package com.voxeo.tropo.actions;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.ValidKeys;

import support.HeadersSupportHandler;

@ValidKeys(keys = {"headers"})
public class AnswerAction extends JsonAction {

  private HeadersSupportHandler headersSupportHandler = new HeadersSupportHandler();

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
