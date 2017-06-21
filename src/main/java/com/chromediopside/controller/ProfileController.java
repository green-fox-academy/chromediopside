package com.chromediopside.controller;

import com.chromediopside.datatransfer.ErrorResponse;
import com.chromediopside.mockbuilder.MockProfileBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> exception(Exception ex){
    ErrorResponse message = new ErrorResponse("Unauthorized request!");
    return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
  }

  @RequestMapping("/profile")
  public ResponseEntity<?> getProfile(@RequestHeader(name = "X-GiTinder-token") String token,
          MockProfileBuilder mockProfile) throws Exception {
      return new ResponseEntity<Object>(mockProfile, HttpStatus.OK);
  }


}
