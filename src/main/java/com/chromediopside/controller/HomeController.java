package com.chromediopside.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nóra on 2017. 06. 07..
 */
@RestController
public class HomeController {

  @RequestMapping("/")
  public String home() {
    return "hello";
  }

}
