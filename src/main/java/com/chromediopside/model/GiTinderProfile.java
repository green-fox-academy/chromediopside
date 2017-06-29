package com.chromediopside.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import org.springframework.stereotype.Component;

@Component
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
  @Column(name = "random_code_links")
  private String randomCodeLinks;

  @ManyToMany
  @JoinTable(
      name = "language_to_profile",
      joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "login"),
      inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "language_name"))
  private Set<Language> languagesList;

  public GiTinderProfile() {
  }

  public GiTinderProfile(String login, String avatarUrl, String repos, Timestamp refreshDate,
      String randomCodeLinks, Set<Language> languagesList) {
    this.login = login;
    this.avatarUrl = avatarUrl;
    this.repos = repos;
    this.refreshDate = refreshDate;
    this.randomCodeLinks = randomCodeLinks;
    this.languagesList = languagesList;
  }

  public GiTinderProfile(String login, String avatarUrl, String repos, Timestamp refreshDate,
      String randomCodeLinks) {
    this.login = login;
    this.avatarUrl = avatarUrl;
    this.repos = repos;
    this.refreshDate = refreshDate;
    this.randomCodeLinks = randomCodeLinks;
  }

  public GiTinderProfile(String login, String avatarUrl, String repos) {
    this.login = login;
    this.avatarUrl = avatarUrl;
    this.repos = repos;
  }

  public void updateProfile(String avatarUrl, String repos,
      Set<Language> languagesList) {
    this.refreshDate = new Timestamp(System.currentTimeMillis());
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

  public Timestamp getRefreshDate() {
    return refreshDate;
  }

  public void setRefreshDate(Timestamp refreshDate) {
    this.refreshDate = refreshDate;
  }

  public String getRandomCodeLinks() {
    return randomCodeLinks;
  }

  public void setRandomCodeLinks(String randomCodeLinks) {
    this.randomCodeLinks = randomCodeLinks;
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
        Objects.equals(languageStringsListFromSet(languagesList),
            languageStringsListFromSet(profile.languagesList));
  }

  private List<String> languageStringsListFromSet(Set<Language> languageSet) {
    List<String> languageStrings = new ArrayList<>();
    for (Language current : languageSet) {
      languageStrings.add(current.getLanguageName());
    }
    Collections.sort(languageStrings);
    return languageStrings;
  }
}
