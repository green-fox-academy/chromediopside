package com.chromediopside.controller;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.chromediopside.GitinderApplication;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GitinderApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class ProfileControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void getProfileWithToken() throws Exception {

    this.mockMvc.perform(get("/profile").header("X-GiTinder-token", "123"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$").value(hasKey("login")))
            .andExpect(jsonPath("$").value(hasKey("avatarUrl")))
            .andExpect(jsonPath("$").value(hasKey("repos")))
            .andExpect(jsonPath("$").value(hasKey("languagesList")))
            .andExpect(jsonPath("$.languagesList").value(
                    anyOf(any(Set.class), nullValue(Set.class))));
  }

  @Test
  public void getProfileWithoutToken() throws Exception {

    mockMvc.perform(get("/profile"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{"
                    + "\"status\" : \"error\","
                    + "\"message\" : \"Unauthorized request!\""
                    + "}"));
  }

  @Test
  public void listAvaialblePagesWithoutToken() throws Exception {

    mockMvc.perform(get("/available"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{"
                    + "\"status\" : \"error\","
                    + "\"message\" : \"Unauthorized request!\""
                    + "}"));
  }

}
