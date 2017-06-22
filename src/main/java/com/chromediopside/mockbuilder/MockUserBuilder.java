package com.chromediopside.mockbuilder;

import com.chromediopside.model.GiTinderUser;
import org.springframework.stereotype.Component;

@Component
public class MockUserBuilder {

  private GiTinderUser giTinderUserProfile;

  public MockUserBuilder() {
    giTinderUserProfile = new GiTinderUser("testa", "a23456789101112a", "aa345678910111aa");
  }

  public MockUserBuilder setUserName(String userName) {
    giTinderUserProfile.setUserName(userName);
    return this;
  }

  public MockUserBuilder setAccessToken(String accessToken) {
    giTinderUserProfile.setAccessToken(accessToken);
    return this;
  }

  public MockUserBuilder setAppToken(String appToken) {
    giTinderUserProfile.setAppToken(appToken);
    return this;
  }

  public GiTinderUser build() {
    return giTinderUserProfile;
  }

  public GiTinderUser getGiTinderUserProfile() {
    return giTinderUserProfile;
  }
}
