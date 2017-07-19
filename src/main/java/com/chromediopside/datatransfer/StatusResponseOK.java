package com.chromediopside.datatransfer;

import org.springframework.stereotype.Component;

@Component
public class StatusResponseOK {

  private String status;

  public StatusResponseOK() {
    status = "ok";
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
