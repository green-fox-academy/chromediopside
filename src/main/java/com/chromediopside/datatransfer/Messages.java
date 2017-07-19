package com.chromediopside.datatransfer;

import com.chromediopside.model.Message;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Messages {

  private List<Message> messages;

  public Messages() {
  }

  public Messages(List<Message> messages) {
    this.messages = messages;
  }

  public List<Message> getMessages() {
    return messages;
  }
}
