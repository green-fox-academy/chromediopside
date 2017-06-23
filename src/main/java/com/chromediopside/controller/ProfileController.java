package com.chromediopside.controller;

import com.chromediopside.model.SwipeDirection;
import com.chromediopside.service.ErrorService;
import com.chromediopside.service.PageService;
import com.chromediopside.service.GiTinderUserService;
import com.chromediopside.service.ProfileService;
import java.util.Map;
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
  private PageService pageService;

  @Autowired
  public ProfileController(ProfileService profileService,
      ErrorService errorService, GiTinderUserService userService, PageService pageService) {
    this.profileService = profileService;
    this.errorService = errorService;
    this.userService = userService;
    this.pageService = pageService;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> exception(Exception ex) {
    return errorService.unauthorizedRequestError();
  }

  @RequestMapping("/profile")
  public ResponseEntity<?> getOwnProfile(@RequestHeader(name = "X-GiTinder-token") String appToken)
          throws Exception {
    return profileService.getOwnProfile(appToken);
  }


  @RequestMapping("/profiles/{username}")
  public ResponseEntity<?> getOtherProfile(@PathVariable String username,
          @RequestHeader(name = "X-GiTinder-token") String accessToken) throws Exception {
    return profileService.getOtherProfile(username, accessToken);
  }

  @GetMapping(value = {"/available/{page}", "/available"})
  public ResponseEntity<?> listAvailableProfilesByPage(
          @RequestHeader(name = "X-GiTinder-token") String appToken,
          @PathVariable Map<String, Integer> pathVariable) throws Exception {
    return profileService.tenProfileByPage(appToken, pathVariable.get("page"));
  }

  @PutMapping("/profiles/{username}/{direction}")
  public ResponseEntity<?> swipe(@RequestHeader (name = "X-GiTinder-token") String appToken,
          @PathVariable String username,
          @PathVariable String direction,
          SwipeDirection swipeDirection) {

    return profileService.handleSwiping(appToken, username, direction, swipeDirection);
  }
}
