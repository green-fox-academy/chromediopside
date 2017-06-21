package com.chromediopside.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gitinder_user")
public class GiTinderUser {

  @Id
  @Column(name = "user_name")
  private String userName;
  @Column (name = "access_token")
  private String accessToken;
  @Column (name = "app_token")
  private String appToken;

  public GiTinderUser() {
  }

  public GiTinderUser(String userName, String accessToken, String appToken) {
    this.userName = userName;
    this.accessToken = accessToken;
    this.appToken = appToken;
  }

  @Override
  public String toString() {
    return userName + ", " + accessToken + ", " + appToken;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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
