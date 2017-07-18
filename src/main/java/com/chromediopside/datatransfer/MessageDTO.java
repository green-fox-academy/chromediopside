package com.chromediopside.datatransfer;

import java.sql.Timestamp;
import org.springframework.stereotype.Component;

@Component
public class MessageDTO {

  private String from;
  private String to;
  private Timestamp createdAt;
  private String message;

  public MessageDTO() {
  }

  public MessageDTO(String from, String to, Timestamp createdAt, String message) {
    this.from = from;
    this.to = to;
    this.createdAt = createdAt;
    this.message = message;
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
