package com.chromediopside.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.chromediopside.GitinderApplication;
import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.datatransfer.TokenResponse;
import com.chromediopside.service.LoginService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GitinderApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class LoginControllerTest {

  private MockMvc mockMvc;

  @Mock
  private LoginService mockLoginService;

  @InjectMocks
  @Autowired
  private LoginController loginController;

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
  public void allParametersGiven() throws Exception {

    LoginForm loginForm = new LoginForm("Bond", "abcd1234");

    TokenResponse tokenResponse = new TokenResponse("s0m3t0k3n");

    Mockito.when(mockLoginService.loginFormContainsValidAccessToken(loginForm)).thenReturn(true);
    Mockito.when(mockLoginService.login(loginForm)).thenReturn(tokenResponse);

    mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
        .content("{"
            + "\"user_name\" : \"Bond\","
            + "\"access_token\" : \"abcd1234\""
            + "}"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").value(hasKey("status")))
        .andExpect(jsonPath("$").value(hasKey("token")));
  }

  @Test
  public void testMissingUsername() throws Exception {
    mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{"
            + "\"access_token\" : \"abcd1234\""
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
            + "\"user_name\" : \"Bond\""
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
