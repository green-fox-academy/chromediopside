package com.chromediopside.controller;

import com.chromediopside.datatransfer.Matches;
import com.chromediopside.service.ErrorService;
import com.chromediopside.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {

  private ErrorService errorService;
  private MatchService matchService;

  @Autowired
  public MatchController(ErrorService errorService,
          MatchService matchService) {
    this.errorService = errorService;
    this.matchService = matchService;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> exception(Exception ex) {
    return new ResponseEntity<>(errorService.unauthorizedRequestError(), HttpStatus.UNAUTHORIZED);
  }

  @CrossOrigin("*")
  @GetMapping("/matches")
  public Matches getMatches(@RequestHeader(name = "X-GiTinder-token") String appToken) {
    return matchService.getMatches(appToken);
  }
}
