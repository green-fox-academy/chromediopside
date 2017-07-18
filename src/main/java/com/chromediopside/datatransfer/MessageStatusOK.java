package com.chromediopside.datatransfer;

import org.springframework.stereotype.Component;

@Component
public class MessageStatusOK {

  String status;
  MessageDTO messageDTO;

  public MessageStatusOK(MessageDTO messageDTO) {
    status = "ok";
    this.messageDTO = messageDTO;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public MessageDTO getMessageDTO() {
    return messageDTO;
  }

  public void setMessageDTO(MessageDTO messageDTO) {
    this.messageDTO = messageDTO;
  }
}
