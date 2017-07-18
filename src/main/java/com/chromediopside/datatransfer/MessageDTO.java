package com.chromediopside.datatransfer;

import java.sql.Timestamp;
import org.springframework.stereotype.Component;

@Component
public class MessageDTO {

  private String to;
  private String message;

  public MessageDTO() {
  }

  public MessageDTO(String to, String message) {
    this.to = to;
    this.message = message;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
