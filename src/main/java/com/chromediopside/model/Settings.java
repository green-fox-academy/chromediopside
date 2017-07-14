package com.chromediopside.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "settings")
public class Settings {

  @Id
  @NotNull
  @JsonIgnore
  @Column(name = "login")
  private String login;
  @Column(name = "enable_notifications")
  @JsonProperty("enable_notifications")
  private boolean enableNotifications;
  @Column(name = "enable_background_sync")
  @JsonProperty("enable_background_sync")
  private boolean enableBackgroundSync;
  @Min(1)
  @Max(100)
  @Column(name = "max_distance")
  @JsonProperty("max_distance")
  private int maxDistance;

  @ManyToMany
  @JoinTable(
      name = "preferred_language_to_profile",
      joinColumns = @JoinColumn(name = "settings_id", referencedColumnName = "login"),
      inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "language_name"))
  @JsonProperty("preferred_languages")
  private Set<Language> preferredLanguages;

  public Settings() {
  }

  public Settings(boolean enableNotifications, boolean enableBackgroundSync, int maxDistance,
      Set<Language> preferredLanguages) {
    this.enableNotifications = enableNotifications;
    this.enableBackgroundSync = enableBackgroundSync;
    this.maxDistance = maxDistance;
    this.preferredLanguages = preferredLanguages;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public boolean isEnableNotifications() {
    return enableNotifications;
  }

  public void setEnableNotifications(boolean enableNotifications) {
    this.enableNotifications = enableNotifications;
  }

  public boolean isEnableBackgroundSync() {
    return enableBackgroundSync;
  }

  public void setEnableBackgroundSync(boolean enableBackgroundSync) {
    this.enableBackgroundSync = enableBackgroundSync;
  }

  public int getMaxDistance() {
    return maxDistance;
  }

  public void setMaxDistance(int maxDistance) {
    this.maxDistance = maxDistance;
  }

  public Set<Language> getPreferredLanguages() {
    return preferredLanguages;
  }

  public void setPreferredLanguages(Set<Language> preferredLanguages) {
    this.preferredLanguages = preferredLanguages;
  }
}
