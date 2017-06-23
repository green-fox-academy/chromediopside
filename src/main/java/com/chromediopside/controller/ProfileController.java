package com.chromediopside.controller;

import com.chromediopside.model.SwipeDirection;
import com.chromediopside.repository.SwipeRepository;
import com.chromediopside.service.ErrorService;
import com.chromediopside.service.GiTinderUserService;
import com.chromediopside.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  private ProfileService profileService;
  private ErrorService errorService;
  private GiTinderUserService userService;

  @Autowired
  SwipeRepository swipeRepository;

  @Autowired
  public ProfileController(ProfileService profileService,
      ErrorService errorService, GiTinderUserService userService) {
    this.profileService = profileService;
    this.errorService = errorService;
    this.userService = userService;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> exception(Exception ex) {
    return errorService.unauthorizedRequestError();
  }

  @RequestMapping("/profile")
  public ResponseEntity<?> getProfile(@RequestHeader(name = "X-GiTinder-token") String appToken)
      throws Exception {
    return profileService.getProfile(appToken);
  }

  @GetMapping("/profiles/{username}")
  public ResponseEntity<?> getProfile(@PathVariable String username,
      @RequestHeader(name = "X-GiTinder-token") String accessToken) throws Exception {
    return profileService.profile(username, accessToken);
  }

  @PutMapping("/profiles/{username}/{direction}")
  public ResponseEntity<?> swipe(@RequestHeader (name = "X-GiTinder-token") String appToken,
          @PathVariable String username,
          @PathVariable String direction,
          SwipeDirection swipeDirection) {

    return profileService.handleSwiping(appToken, username, direction, swipeDirection);
  }
}
