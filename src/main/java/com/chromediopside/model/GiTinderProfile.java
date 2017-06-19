package com.chromediopside.model;

import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "gitinder_profile")
public class GiTinderProfile {

  @Id
  @NotNull
  @Column(name = "login")
  private String login;
  @Column(name = "avatarurl")
  private String avatarUrl;
  private String repos;

  @ManyToMany
  @JoinTable(
      name = "LANGUAGE_TO_USER",
      joinColumns = @JoinColumn(name = "PROFILE_ID", referencedColumnName = "login"),
      inverseJoinColumns = @JoinColumn(name = "LANGUAGE_ID", referencedColumnName = "languagename"))
  private Set<Language> languagesList;

  public GiTinderProfile() {
  }

  public GiTinderProfile(String login, String avatarUrl, String repos,
      Set<Language> languagesList) {
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

  public Set<Language> getLanguagesList() {
    return languagesList;
  }

  public void setLanguagesList(Set<Language> languagesList) {
    this.languagesList = languagesList;
  }
}
