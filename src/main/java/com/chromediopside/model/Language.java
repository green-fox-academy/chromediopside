package com.chromediopside.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "language")
public class Language {

  @Id
  @Column(name = "language_name")
  private String languageName;

  public Language(String languageName) {
    this.languageName = languageName;
  }

  @Override
  public String toString() {
    return languageName;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Language)) {
      return false;
    }
    Language other = (Language) obj;
    return this.languageName.equals(other.languageName);
  }

  @Override
  public int hashCode() {
    return this.languageName.hashCode();
  }

  public Language() {
  }

  public String getLanguageName() {
    return languageName;
  }

  public void setLanguageName(String languageName) {
    this.languageName = languageName;
  }
}
