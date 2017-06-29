package com.chromediopside.datatransfer;

import com.chromediopside.model.Match;
import org.springframework.stereotype.Component;

@Component
public class SwipeResponse {

  String status;
  String message;
  Match match;

  public SwipeResponse() {
    status = "ok";
    message = "success";
  }

  public SwipeResponse(Match match) {
    status = "ok";
    message = "success";
    this.match = match;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public Match getMatch() {
    return match;
  }

  public void setMatch(Match match) {
    this.match = match;
  }
}
