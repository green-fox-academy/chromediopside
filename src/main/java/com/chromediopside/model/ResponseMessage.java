package com.chromediopside.model;

import org.springframework.stereotype.Component;

/**
 * Created by Nóra on 2017. 06. 19..
 */
@Component
public class ResponseMessage {

  private String status;
  private String message;

  public ResponseMessage() {
  }

  public ResponseMessage(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "ResponseMessage{" +
            "status='" + status + '\'' +
            ", message='" + message + '\'' +
            '}';
  }
}
