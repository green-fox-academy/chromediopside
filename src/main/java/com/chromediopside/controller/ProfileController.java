package com.chromediopside.controller;

import com.chromediopside.datatransfer.ErrorResponse;
import com.chromediopside.model.GiTinderProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  @RequestMapping("/profile")
  public ResponseEntity<?> getProfile(@RequestHeader(name = "X-GiTinder-token", required=false) String token) {

    try {
      if (!token.equals(null)) {
        GiTinderProfile mockProfile = new GiTinderProfile();
        mockProfile.setLogin("kondfox");
        mockProfile.setAvatarUrl("https://avatars1.githubusercontent.com/u/26329189?v=3");
        mockProfile.setRepos("repo1;repo2;repo3");
        return new ResponseEntity<Object>(mockProfile, HttpStatus.OK);
      } else {
        ErrorResponse message = new ErrorResponse("Unauthorized request!");
        return new ResponseEntity<Object>(message, HttpStatus.UNAUTHORIZED);
      }
    } catch (Exception ex){
      ErrorResponse message = new ErrorResponse("Unauthorized request!");
      return new ResponseEntity<Object>(message, HttpStatus.UNAUTHORIZED);
    }
  }
}
