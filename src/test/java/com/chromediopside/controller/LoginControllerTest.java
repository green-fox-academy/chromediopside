package com.chromediopside.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.chromediopside.GitinderApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GitinderApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class LoginControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void controllerTestContextLoads() throws Exception {
  }

  @Test
  public void testAllParametersOk() throws Exception {
    mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{"
            + "\"username\" : \"Bond\","
            + "\"accessToken\" : \"abcd1234\""
            + "}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").value(hasKey("status")))
        .andExpect(jsonPath("$").value(hasKey("token")));
  }

  @Test
  public void testMissingUsername() throws Exception {
    mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{"
            + "\"accessToken\" : \"abcd1234\""
            + "}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json("{"
            + "\"status\": \"error\","
            + "\"message\" : \"Missing parameter(s): username!\""
            + "}"));
  }

  @Test
  public void testMissingAccessToken() throws Exception {
    mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{"
            + "\"username\" : \"Bond\""
            + "}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json("{"
            + "\"status\": \"error\","
            + "\"message\" : \"Missing parameter(s): accessToken!\""
            + "}"));
  }

  @Test
  public void testBothParametersMissing() throws Exception {
    mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{"
            + "}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json("{"
            + "\"status\": \"error\","
            + "\"message\" : \"Missing parameter(s): accessToken, username!\""
            + "}"));
  }
}
