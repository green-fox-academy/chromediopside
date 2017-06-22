package com.chromediopside.datatransfer;

import org.springframework.stereotype.Component;

@Component
public class TokenResponse {

  private String status;
  private String token;

  public TokenResponse() {
  }

  public TokenResponse(String token) {
    this.token = token;
    status = "ok";
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
