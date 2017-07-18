package com.chromediopside.service;

import com.chromediopside.datatransfer.MessageDTO;
import com.chromediopside.datatransfer.Messages;
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

  public Messages getConversationBetweenUsers(String appToken, String username) {
    GiTinderUser actualUser = giTinderUserService.getUserByAppToken(appToken);
    String actualUsersName = actualUser.getUserName();
    List<Message> conversation = messageRepository.findMessagesBetween(actualUsersName, username);
    Messages messages = new Messages(conversation);
    return messages;
  }

  public void deleteMessage(long id) {
    messageRepository.delete(id);
  }

  public Message postMessage(MessageDTO messageDTO, String appToken) {
    GiTinderUser actualUser = giTinderUserService.getUserByAppToken(appToken);
    String actualUsersName = actualUser.getUserName();
    Message messageToSave = new Message(actualUsersName, messageDTO.getTo(), messageDTO.getMessage());
    messageRepository.save(messageToSave);
    return messageToSave;
  }
}
