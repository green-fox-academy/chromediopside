package com.chromediopside.datatransfer;

import com.chromediopside.model.GiTinderProfile;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof LoginForm)) {
      return false;
    }
    LoginForm form = (LoginForm) o;
    return Objects.equals(username, form.username) &&
        Objects.equals(accessToken, form.accessToken);
  }
}
