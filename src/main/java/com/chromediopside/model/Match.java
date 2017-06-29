package com.chromediopside.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class Match {

  private String username;
  private Timestamp matchedAt;
  private ArrayList<String> messages;

  public Match() {
  }

  public Match(String username, Timestamp matchedAt) {
    this.username = username;
    this.matchedAt = matchedAt;
  }

  public String getUsername() {
    return username;
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

  public void setMatched_at(Timestamp matched_at) {
    this.matchedAt = matched_at;
  }
}
