package com.chromediopside.controller;

import com.chromediopside.datatransfer.ErrorResponse;
import com.chromediopside.mockbuilder.MockProfileBuilder;
import com.chromediopside.service.ErrorService;
import com.chromediopside.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  private ProfileService profileService;
  private ErrorService errorService;

  @Autowired
  public ProfileController(ProfileService profileService,
          ErrorService errorService) {
    this.profileService = profileService;
    this.errorService = errorService;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> exception(Exception ex){
    return errorService.getUnauthorizedResponseEntity();
  }

  @RequestMapping("/profile")
  public ResponseEntity<?> getProfile(@RequestHeader(name = "X-GiTinder-token") String accsessToken,
          MockProfileBuilder mockProfile) throws Exception {
      return profileService.getProfile(accsessToken, mockProfile);
  }


}
