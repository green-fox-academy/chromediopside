package com.chromediopside.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class GiTinderProfile {
  @Id
  @NotNull
  @Column(name="login")
  private String login;
  private String avatarUrl;
  private String repos;

  @ManyToMany
  @JoinTable(
      name="LANGUAGE_TO_USER",
      joinColumns = @JoinColumn(name="PROFILE_ID", referencedColumnName = "login"),
      inverseJoinColumns = @JoinColumn(name="LANGUAGE_ID", referencedColumnName = "id"))
  private List<Language> languagesList;

  public GiTinderProfile() {
  }

  public GiTinderProfile(String login, String avatarUrl, String repos,
          List<Language> languagesList) {
    this.login = login;
    this.avatarUrl = avatarUrl;
    this.repos = repos;
    this.languagesList = languagesList;
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

  public String getRepos() {
    return repos;
  }

  public void setRepos(String repos) {
    this.repos = repos;
  }

  public List<Language> getLanguagesList() {
    return languagesList;
  }

  public void setLanguagesList(List<Language> languagesList) {
    this.languagesList = languagesList;
  }
}
