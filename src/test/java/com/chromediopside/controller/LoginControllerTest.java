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
import com.chromediopside.service.ErrorService;
import com.chromediopside.service.LoginService;
import com.chromediopside.service.ProfileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

  @Autowired
  private LoginController loginController;

  @Mock
  private LoginService mockLoginService;

  @Mock
  private ErrorService mockErrorService;

  @Mock
  private ProfileService mockProfileService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void controllerTestContextLoads() throws Exception {
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testAllParametersOk() throws Exception {
    loginController.setLoginService(mockLoginService);
    loginController.setProfileService(mockProfileService);

    LoginForm loginForm = new LoginForm();
    loginForm.setUsername("Bond");
    loginForm.setAccessToken("abcd1234");

    TokenResponse tokenResponse = new TokenResponse();
    tokenResponse.setToken("s0m3t0k3n");
    tokenResponse.setStatus("ok");

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);

    ResponseEntity responseEntity = new ResponseEntity<>(tokenResponse, headers, HttpStatus.OK);

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
