package com.chromediopside.datatransfer;

import com.chromediopside.model.GiTinderProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.List;

public class ProfileResponse {

  private String login;
  @JsonProperty("avatar_url")
  private String avatarUrl;
  private List<String> repos;
  private List<String> languages;
  private List<String> snippets;

  public ProfileResponse() {
  }

  public ProfileResponse(GiTinderProfile giTinderProfile) {
    login = giTinderProfile.getLogin();
    avatarUrl = giTinderProfile.getAvatarUrl();
    if (giTinderProfile.getRepos() != null) {
      repos = Arrays.asList(giTinderProfile.getRepos().split(";"));
    }
    if (giTinderProfile.getLanguagesList() != null) {
      languages = giTinderProfile.languageStringsListFromSet(giTinderProfile.getLanguagesList());
    }
    if (giTinderProfile.getRandomCodeLinks() != null) {
      snippets = Arrays.asList(giTinderProfile.getRandomCodeLinks().split(";"));
    }
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public List<String> getRepos() {
    return repos;
  }

  public void setRepos(List<String> repos) {
    this.repos = repos;
  }

  public List<String> getLanguages() {
    return languages;
  }

  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }

  public List<String> getSnippets() {
    return snippets;
  }

  public void setSnippets(List<String> snippets) {
    this.snippets = snippets;
  }
}
