package com.chromediopside.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @CrossOrigin("*")
  @RequestMapping("/")
  public String home() {
    return "hello";
  }
}
