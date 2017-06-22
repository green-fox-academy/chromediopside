package com.chromediopside.model;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
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
  @Column(name = "avatar_url")
  private String avatarUrl;
  @Column(name = "repos")
  private String repos;
  @Column(name = "refresh_date")
  private Timestamp refreshDate;

  @ManyToMany
  @JoinTable(
      name = "language_to_profile",
      joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "login"),
      inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "language_name"))
  private Set<Language> languagesList;

  public GiTinderProfile() {
  }

  public GiTinderProfile(String login, String avatarUrl, String repos,
      Timestamp refreshDate, Set<Language> languagesList) {
    this.login = login;
    this.avatarUrl = avatarUrl;
    this.repos = repos;
    this.refreshDate = refreshDate;
    this.languagesList = languagesList;
  }

  public GiTinderProfile(String login, String avatarUrl, String repos) {
    this.login = login;
    this.avatarUrl = avatarUrl;
    this.repos = repos;
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

  public Timestamp getRefreshDate() {
    return refreshDate;
  }

  public void setRefreshDate(Timestamp refreshDate) {
    this.refreshDate = refreshDate;
  }

  @Override
  public boolean equals(Object o) {

    if (!(o instanceof GiTinderProfile)) {
      return false;
    }

    GiTinderProfile profile = (GiTinderProfile) o;

    return Objects.equals(login, profile.login) &&
        Objects.equals(avatarUrl, profile.avatarUrl) &&
        Objects.equals(repos, profile.repos) &&
        Objects.equals(languagesList, profile.languagesList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(login, avatarUrl, repos, refreshDate, languagesList);
  }

  @Override
  public String toString() {
    return login + "\n" +
        avatarUrl + "\n" +
        repos + "\n" +
        languagesList.toString();
  }
}
