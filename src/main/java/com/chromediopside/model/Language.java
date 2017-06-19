package com.chromediopside.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Language {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "languageName")
  private String languageName;

  @JoinColumn(name = "login")
  private String login;

  public Language(String languageName, String login) {
    this.languageName = languageName;
    this.login = login;
  }

  @Override
  public String toString() {
    return languageName;
  }

  @Override
  public boolean equals(Object obj) {
    Language other = (Language) obj;
    return this.languageName == other.languageName;
  }

  @Override
  public int hashCode() {
    return this.languageName.hashCode();
  }

  public Language() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLanguageName() {
    return languageName;
  }

  public void setLanguageName(String languageName) {
    this.languageName = languageName;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }
}
