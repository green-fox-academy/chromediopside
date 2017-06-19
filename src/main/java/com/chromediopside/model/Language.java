package com.chromediopside.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Language {

  @Id
  @Column(name = "languagename")
  private String languageName;

  @ManyToMany(mappedBy = "languagesList")
  private Set<GiTinderProfile> profileSet;

  public Language(String languageName) {
    this.languageName = languageName;
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

  public String getLanguageName() {
    return languageName;
  }

  public void setLanguageName(String languageName) {
    this.languageName = languageName;
  }

  public Set<GiTinderProfile> getProfileSet() {
    return profileSet;
  }

  public void setProfileSet(Set<GiTinderProfile> profileSet) {
    this.profileSet = profileSet;
  }
}
