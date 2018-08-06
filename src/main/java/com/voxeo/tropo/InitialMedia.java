package com.voxeo.tropo;

import java.io.Serializable;

public class InitialMedia implements Serializable {

  private static final long serialVersionUID = 610952828909985978L;

  private String text;

  private String media;

  private String status;

  private String disposition;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getMedia() {
    return media;
  }

  public void setMedia(String media) {
    this.media = media;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDisposition() {
    return disposition;
  }

  public void setDisposition(String disposition) {
    this.disposition = disposition;
  }

}
