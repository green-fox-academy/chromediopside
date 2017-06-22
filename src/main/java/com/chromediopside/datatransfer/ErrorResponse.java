package com.chromediopside.datatransfer;

import org.springframework.stereotype.Component;

@Component
public class ErrorResponse {

  private String status = "error";
  private String message;

  public ErrorResponse() {
  }

  public ErrorResponse(String message) {
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
