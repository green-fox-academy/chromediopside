package com.chromediopside.service;

import com.chromediopside.datatransfer.MessageDTO;
import com.chromediopside.model.GiTinderUser;
import com.chromediopside.model.Message;
import com.chromediopside.repository.MessageRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private MessageRepository messageRepository;
  private GiTinderUserService giTinderUserService;

  @Autowired
  public MessageService(MessageRepository messageRepository,
          GiTinderUserService giTinderUserService) {
    this.messageRepository = messageRepository;
    this.giTinderUserService = giTinderUserService;
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

  public Message saveMessage(MessageDTO messageDTO, String appToken) {
    GiTinderUser actualUser = giTinderUserService.getUserByAppToken(appToken);
    String actualUsersName = actualUser.getUserName();
    Message messageToSave = new Message(actualUsersName, messageDTO.getTo(), messageDTO.getMessage());
    messageRepository.save(messageToSave);
    return messageToSave;
  }
}
