package com.chromediopside.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.chromediopside.GitinderApplication;
import com.chromediopside.mockbuilder.MockUserBuilder;
import com.chromediopside.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GitinderApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableWebMvc
public class MessageControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;
  @MockBean
  UserRepository userRepository;

  @Autowired
  MockUserBuilder mockUserBuilder;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }


  @Test
  public void postMessageWithoutAppTokenAndBody() throws Exception {
    mockMvc.perform(post("/messages"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{"
                    +"\"status\": \"error\","
                    +"\"message\": \"Unauthorized request!\"}"));
  }

  @Test
  public void postMessageWithoutBody() throws Exception {
    Mockito.when(userRepository.findByAppToken("123")).thenReturn(mockUserBuilder.build());

    mockMvc.perform(post("/messages").header("X-GiTinder-token", "123"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{"
                    +"\"status\": \"error\","
                    +"\"message\": \"Required request body is missing!\"}"));
  }

}
