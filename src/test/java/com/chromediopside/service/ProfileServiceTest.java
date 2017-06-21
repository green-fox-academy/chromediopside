package com.chromediopside.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.Language;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfileServiceTest {


  private static final String validAccessToken = System.getenv("TEST_ACCESS_TOKEN");
  private static final String invalidAccessToken = "1nval1dt0k3n";
  private static final String testLogin = System.getenv("TEST_LOGIN");
  private static final String testAvatarUrl = System.getenv("TEST_AVATAR_URL");
  private static final String testRepos = System.getenv("TEST_REPOS");
  private Set<Language> testLanguagesList = new HashSet<>();

  @Before
  public void setup() throws Exception {
    testLanguagesList.add(new Language("Java"));
    testLanguagesList.add(new Language("HTML"));
  }

  @Test
  public void validAccessToken() throws Exception {
    ProfileService profileService = new ProfileService();
    GiTinderProfile expectedProfile = new GiTinderProfile(testLogin,testAvatarUrl, testRepos, testLanguagesList);
    GiTinderProfile actualProfile = profileService.getProfileFromGitHub(validAccessToken);
    assertEquals(expectedProfile, actualProfile);
  }


  @Test
  public void invalidAccessToken() {
    ProfileService profileService = new ProfileService();
    GiTinderProfile actualProfile = profileService.getProfileFromGitHub(invalidAccessToken);
    assertNull(actualProfile);
  }
}
