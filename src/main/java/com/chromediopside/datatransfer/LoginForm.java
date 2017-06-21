package com.chromediopside.datatransfer;

import javax.validation.constraints.NotNull;

public class LoginForm {

  @NotNull
  private String username;
  @NotNull
  private String accessToken;

  public LoginForm() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
