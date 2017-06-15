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
public class Language {
  @Id
  @NotNull
  @Column(name="languageName")
  private String languageName;

  @Transient
  @ManyToMany
  @JoinTable(
      name="LANGUAGE_TO_USER",
      joinColumns = @JoinColumn(name="LANGUAGE_ID", referencedColumnName = "languageName"),
      inverseJoinColumns = @JoinColumn(name="PROFILE_ID", referencedColumnName = "login"))
  private List<GiTinderProfile> profileList;
}