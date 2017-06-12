package com.chromediopside.datatransfer;

public class TokenResponse {

    private String status, token;

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
