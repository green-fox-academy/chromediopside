package com.chromediopside.controller;


import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.chromediopside.GitinderApplication;
import com.chromediopside.mockbuilder.MockSettingsBuilder;
import com.chromediopside.mockbuilder.MockUserBuilder;
import com.chromediopside.repository.SettingRepository;
import com.chromediopside.repository.UserRepository;
import com.chromediopside.service.ProfileService;
import java.util.Set;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GitinderApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableWebMvc
public class SettingsControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  private SettingRepository settingRepository;
  @MockBean
  private UserRepository userRepository;
  @Autowired
  private ProfileService profileService;
  @Autowired
  private MockSettingsBuilder mockSettingsBuilder;
  @Autowired
  private MockUserBuilder mockUserBuilder;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void getSettingsWithoutToken() throws Exception {

    this.mockMvc.perform(get("/settings"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{"
                    + "\"status\" : \"error\","
                    + "\"message\" : \"Unauthorized request!\""
                    + "}"));
  }

  @Test
  public void getSettingsWithToken() throws Exception {

    Mockito.when(userRepository.findByAppToken("abcdef")).thenReturn(mockUserBuilder.build());
    Mockito.when(settingRepository.findByLogin("kondfox")).thenReturn(mockSettingsBuilder.build());

    mockMvc.perform(get("/settings").header("X-GiTinder-token", "abcdef"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$").value(hasKey("enable_notifications")))
            .andExpect(jsonPath("$").value(hasKey("enable_background_sync")))
            .andExpect(jsonPath("$").value(hasKey("max_distance")))
            .andExpect(jsonPath("$").value(hasKey("preferred_languages")))
            .andExpect(jsonPath("$.preferred_languages").value(
                    anyOf(any(Set.class), nullValue(Set.class))));
  }

  @Test
  public void updateSettingsWithoutToken() throws Exception {

    this.mockMvc.perform(put("/settings"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{"
                    + "\"status\" : \"error\","
                    + "\"message\" : \"Unauthorized request!\""
                    + "}"));
  }

  @Test
  public void updateSettingsWithTokenAllFieldsProvided() throws Exception {

    Mockito.when(userRepository.findByAppToken("abcdef")).thenReturn(mockUserBuilder.build());
    Mockito.when(settingRepository.findByLogin("kondfox")).thenReturn(mockSettingsBuilder.build());

    mockMvc.perform(get("/settings").header("X-GiTinder-token", "abcdef")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                    + "\"enable_notifications\": true,\n"
                    + "\"enable_background_sync\": true,\n"
                    + "\"max_distance\": 50,\n"
                    + "\"preferred_languages\": [\n"
                    + "\"Java\",\n"
                    + "\"Python\",\n"
                    + "\"Javascript\"\n"
                    + "]\n"
                    + "}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$").value(hasKey("enable_notifications")))
            .andExpect(jsonPath("$").value(hasKey("enable_background_sync")))
            .andExpect(jsonPath("$").value(hasKey("max_distance")))
            .andExpect(jsonPath("$").value(hasKey("preferred_languages")))
            .andExpect(jsonPath("$.enable_notifications").value(true))
            .andExpect(jsonPath("$.enable_background_sync").value(true))
            .andExpect(jsonPath("$.max_distance").value(50))
            .andExpect(jsonPath("$.enable_notifications").value(true))
            .andExpect(jsonPath("$.preferred_languages").value(
                    anyOf(any(Set.class), nullValue(Set.class))));

  }
}
