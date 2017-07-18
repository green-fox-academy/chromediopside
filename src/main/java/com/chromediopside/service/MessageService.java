package com.chromediopside.service;

import com.chromediopside.datatransfer.MessageDTO;
import com.chromediopside.model.Message;
import com.chromediopside.repository.MessageRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  MessageRepository messageRepository;

  @Autowired
  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public List<Message> getConversationBetweenUsers(String user1, String user2) {
    List<Message> messagesFromUser1 = messageRepository.findByFromInAndToIn(user1, user2);
    List<Message> messagesFromUser2 = messageRepository.findByFromInAndToIn(user2, user1);
    List<Message> conversation = new ArrayList<>(messagesFromUser1.size() + messagesFromUser2.size());
    conversation.addAll(messagesFromUser1);
    conversation.addAll(messagesFromUser2);
    return conversation;
  }

  public void deleteMessage(long id) {
    messageRepository.delete(id);
  }

  public void saveMessage(MessageDTO messageDTO) {
    Message messageToSave = new Message(messageDTO.getFrom(), messageDTO.getTo(), messageDTO.getCreatedAt(), messageDTO.getMessage());
    messageRepository.save(messageToSave);
  }
}
