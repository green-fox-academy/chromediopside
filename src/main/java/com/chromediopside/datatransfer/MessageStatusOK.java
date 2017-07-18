package com.chromediopside.datatransfer;

import com.chromediopside.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageStatusOK {

  private String status;
  private Message message;

  public MessageStatusOK(Message message) {
    status = "ok";
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }
}
