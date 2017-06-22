package com.chromediopside.controller;

import com.chromediopside.service.ErrorService;
import com.chromediopside.service.PageService;
import com.chromediopside.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  private ProfileService profileService;
  private ErrorService errorService;
  private PageService pageService;

  @Autowired
  public ProfileController(ProfileService profileService,
          ErrorService errorService, PageService pageService) {
    this.profileService = profileService;
    this.errorService = errorService;
    this.pageService = pageService;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> exception(Exception ex) {
    return errorService.getUnauthorizedResponseEntity();
  }

  @RequestMapping("/profile")
  public ResponseEntity<?> getProfile(@RequestHeader(name = "X-GiTinder-token") String accessToken)
          throws Exception {
    return profileService.getProfile(accessToken);
  }

  @GetMapping("/available/{page}")
  public ResponseEntity<?> listAvailableProfiles(
          @RequestHeader(name = "X-GiTinder-token") String appToken,
          @PathVariable("page") int pageNumber,
          @RequestBody (required = false) String sortingParam,
          @RequestBody (required = false) String languageName) throws Exception {
    profileService.getProfile(appToken);
    return profileService.tenProfileByPage(pageService.setPage(languageName, sortingParam, pageNumber));
  }

}
