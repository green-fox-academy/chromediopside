package com.chromediopside.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "message")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @NotNull
  private long id;
  @NotNull
  @Column(name = "\"from\"")
  private String from;
  @NotNull
  @Column(name = "\"to\"")
  private String to;
  @JsonProperty("created_at")
  @Column(name = "created_at")
  @NotNull
  private Timestamp createdAt;
  @NotNull
  private String message;

  public Message() {
  }

  public Message(String from, String to, String message) {
    this.from = from;
    this.to = to;
    this.createdAt = new Timestamp(System.currentTimeMillis());
    this.message = message;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
