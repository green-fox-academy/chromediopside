package com.chromediopside;

import com.chromediopside.service.ProfileService;
import java.io.IOException;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GitinderApplication {

  public static void main(String[] args) throws IOException, JSONException {
    SpringApplication.run(com.chromediopside.GitinderApplication.class, args);
    System.out.println("Hello World!");
    ProfileService profileService = new ProfileService();
    profileService.getGithubProfileData("Drumbumdeedum");
  }
}
