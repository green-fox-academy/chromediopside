package com.chromediopside.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Message {

  @Id
  private long id;
  private String from;
  private String to;
  private Timestamp createdAt;
  private String message;

  public Message() {
  }

  public long getId() {
    return id;
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public String getMessage() {
    return message;
  }
}
