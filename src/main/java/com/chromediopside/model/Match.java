package com.chromediopside.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class Match {

  private String username;
  private String avatarUrl;
  private Timestamp matchedAt;
  private ArrayList<String> messages;

  public Match() {
  }

  public Match(String username, String avatarUrl, Timestamp matchedAt) {
    this.username = username;
    this.avatarUrl = avatarUrl;
    this.matchedAt = matchedAt;
  }

  public String getUsername() {
    return username;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public Timestamp getMatched_at() {
    return matchedAt;
  }

  public ArrayList<String> getMessages() {
    return messages;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public void setMatched_at(Timestamp matched_at) {
    this.matchedAt = matched_at;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Match)) {
      return false;
    }
    Match match = (Match) o;
    return Objects.equals(username, match.username)
            && Objects.equals(avatarUrl, match.avatarUrl)
            && Objects.equals(matchedAt, match.matchedAt)
            && Objects.equals(messages, match.messages);
  }
}
