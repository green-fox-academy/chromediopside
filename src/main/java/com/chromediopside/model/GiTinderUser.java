package com.chromediopside.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gitinder_user")
public class GiTinderUser {

  @Id
  private String username;
  private String accessToken;
  private String appToken;

  public GiTinderUser() {
  }

  public GiTinderUser(String username) {
    this.username = username;
  }

  public GiTinderUser(String username, String accessToken, String appToken) {
    this.username = username;
    this.accessToken = accessToken;
    this.appToken = appToken;
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

  public String getAppToken() {
    return appToken;
  }

  public void setAppToken(String appToken) {
    this.appToken = appToken;
  }
}
