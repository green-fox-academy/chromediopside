package com.chromediopside.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class Match {

  private String username;
  private Timestamp matched_at;
  private ArrayList<String> messages;

  public Match() {
  }

  public Match(String username, Timestamp matched_at) {
    this.username = username;
    this.matched_at = matched_at;
  }

  public String getUsername() {
    return username;
  }

  public Timestamp getMatched_at() {
    return matched_at;
  }

  public ArrayList<String> getMessages() {
    return messages;
  }
}
