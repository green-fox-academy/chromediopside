package com.chromediopside.controller;

import com.chromediopside.datatransfer.MessageDTO;
import com.chromediopside.datatransfer.MessageStatusOK;
import com.chromediopside.datatransfer.StatusResponseOK;
import com.chromediopside.model.Message;
import com.chromediopside.service.ErrorService;
import com.chromediopside.service.GiTinderUserService;
import com.chromediopside.service.MatchService;
import com.chromediopside.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

  private MessageService messageService;
  private GiTinderUserService userService;
  private ErrorService errorService;
  private MatchService matchService;

  @Autowired
  public MessageController(MessageService messageService,
      GiTinderUserService userService,
      ErrorService errorService,
      MatchService matchService) {
    this.messageService = messageService;
    this.userService = userService;
    this.errorService = errorService;
    this.matchService = matchService;
  }

  @GetMapping(value = "/messages/{username}")
  public ResponseEntity<Object> getMessages(
          @RequestHeader(name = "X-GiTinder-token") String appToken,
          @PathVariable String otherUser) {
    if (!userService.validAppToken(appToken)) {
      return new ResponseEntity<>(errorService.unauthorizedRequestError(), HttpStatus.UNAUTHORIZED);
    }
    String currentUserName = userService.getUserByAppToken(appToken).getUserName();
    return new ResponseEntity<>(messageService.getConversationBetweenUsers(currentUserName, otherUser), HttpStatus.OK);
  }

  @PostMapping(value = "/messages")
  public ResponseEntity<Object> postMessage(
          @RequestHeader(name = "X-GiTinder-token") String appToken,
          @RequestBody MessageDTO messageDTO) {
    if (!userService.validAppToken(appToken) || !matchService.areTheyMatched(appToken, messageDTO)) {
      return new ResponseEntity<>(errorService.unauthorizedRequestError(), HttpStatus.UNAUTHORIZED);
    }
    Message message = messageService.postMessage(messageDTO, appToken);
    MessageStatusOK messageStatusOK = new MessageStatusOK(message);
    return new ResponseEntity(messageStatusOK, HttpStatus.OK);
  }

  @DeleteMapping(value = "/messages/{id}")
  public ResponseEntity<Object> deleteMessage(
          @RequestHeader(name = "X-GiTinder-token") String appToken,
          @PathVariable long id) {
    if (!userService.validAppToken(appToken)) {
      return new ResponseEntity<>(errorService.unauthorizedRequestError(), HttpStatus.UNAUTHORIZED);
    }
    messageService.deleteMessage(id);
    return new ResponseEntity<>(new StatusResponseOK(), HttpStatus.OK);
  }
}
