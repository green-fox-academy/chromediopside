package com.chromediopside.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class GiTinderProfile {
  @Id
  @NotNull
  @Column(name="login")
  private String login;
  private String avatarUrl;
  private String repos;

  @Transient
  @ManyToMany
  @JoinTable(
      name="LANGUAGE_TO_USER",
      joinColumns = @JoinColumn(name="PROFILE_ID", referencedColumnName = "login"),
      inverseJoinColumns = @JoinColumn(name="LANGUAGE_ID", referencedColumnName = "languageName"))
  private List<Language> languagesList;
}
