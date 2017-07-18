package com.chromediopside.controller;

import com.chromediopside.datatransfer.MessageDTO;
import com.chromediopside.datatransfer.MessageStatusOK;
import com.chromediopside.datatransfer.StatusResponseOK;
import com.chromediopside.service.ErrorService;
import com.chromediopside.service.GiTinderUserService;
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

  MessageService messageService;
  GiTinderUserService userService;
  ErrorService errorService;

  @Autowired
  public MessageController(MessageService messageService,
      GiTinderUserService userService, ErrorService errorService) {
    this.messageService = messageService;
    this.userService = userService;
    this.errorService = errorService;
  }

  @GetMapping(value = "/messages/{to}")
  public ResponseEntity<Object> getMessages(@RequestHeader(name = "X-GiTinder-token") String appToken, @PathVariable String otherUser) {
    if (!userService.validAppToken(appToken)) {
      return new ResponseEntity<>(errorService.unauthorizedRequestError(), HttpStatus.UNAUTHORIZED);
    }
    String currentUserName = userService.getUserByAppToken(appToken).getUserName();
    return new ResponseEntity<>(messageService.getConversationBetweenUsers(currentUserName, otherUser), HttpStatus.OK);
  }

  @PostMapping(value = "/messages")
  public ResponseEntity<Object> postMessage(@RequestHeader(name = "X-GiTinder-token") String appToken, @RequestBody MessageDTO messageDTO) {
    if (!userService.validAppToken(appToken)) {
      return new ResponseEntity<>(errorService.unauthorizedRequestError(), HttpStatus.UNAUTHORIZED);
    }
    messageService.saveMessage(messageDTO);
    return new ResponseEntity<>(new MessageStatusOK(messageDTO), HttpStatus.OK);
  }

  @DeleteMapping(value = "/messages/{id}")
  public ResponseEntity<Object> deleteMessage(@RequestHeader(name = "X-GiTinder-token") String appToken, @PathVariable long id) {
    if (!userService.validAppToken(appToken)) {
      return new ResponseEntity<>(errorService.unauthorizedRequestError(), HttpStatus.UNAUTHORIZED);
    }
    messageService.deleteMessage(id);
    return new ResponseEntity<>(new StatusResponseOK(), HttpStatus.OK);
  }
}
