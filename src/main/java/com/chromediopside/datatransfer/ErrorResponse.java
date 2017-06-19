package com.chromediopside.datatransfer;

public class ErrorResponse {

  private String status, message;

  public ErrorResponse() {
  }

  public ErrorResponse(String message) {
    this.message = message;
    status = "error";
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
