package com.chromediopside.controller;

import com.chromediopside.service.ErrorService;
import com.chromediopside.service.GiTinderUserService;
import com.chromediopside.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettingsController {

  private GiTinderUserService userService;
  private ErrorService errorService;
  private SettingsService settingsService;

  @Autowired
  public SettingsController(GiTinderUserService userService, ErrorService errorService, SettingsService settingsService) {
    this.userService = userService;
    this.errorService = errorService;
    this.settingsService = settingsService;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> exception(Exception ex) {
    return new ResponseEntity<>(errorService.unauthorizedRequestError(), HttpStatus.UNAUTHORIZED);
  }

  @CrossOrigin("*")
  @GetMapping("/settings")
  public ResponseEntity<Object> getUserSettings(
          @RequestHeader(name = "X-GiTinder-token") String appToken) {
    if (!userService.validAppToken(appToken)) {
      return new ResponseEntity<>(errorService.unauthorizedRequestError(), HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(settingsService.getUserSettings(appToken), HttpStatus.OK);
  }

}
