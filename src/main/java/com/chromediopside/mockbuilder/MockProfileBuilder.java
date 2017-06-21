package com.chromediopside.mockbuilder;

import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.Language;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class MockProfileBuilder {

  private GiTinderProfile profile;

  public MockProfileBuilder() {
    profile = new GiTinderProfile("kondfox",
            "https://avatars1.githubusercontent.com/u/26329189?v=3",
            "repo1;repo2;repo3");
  }

  public MockProfileBuilder setLogin(String login) {
    profile.setLogin(login);
    return this;
  }

  public MockProfileBuilder setAvatarUrl(String avatarUrl) {
    profile.setAvatarUrl(avatarUrl);
    return this;
  }

  public MockProfileBuilder setRepos(String repos) {
    profile.setRepos(repos);
    return this;
  }

  public MockProfileBuilder setLanguagesList(Set<Language> languagesList) {
    profile.setLanguagesList(languagesList);
    return this;
  }

  public GiTinderProfile build() {
    return profile;
  }

  public GiTinderProfile getProfile() {
    return profile;
  }
}
