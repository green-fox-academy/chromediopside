package com.chromediopside.datatransfer;

import org.springframework.stereotype.Component;

@Component
public class SwipeResponse {

  String status;
  String message;
  boolean match_status;

  public SwipeResponse() {

  }

  public SwipeResponse(boolean match_status) {
    status = "ok";
    message = "success";
    this.match_status = match_status;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public boolean isMatch_status() {
    return match_status;
  }

}
