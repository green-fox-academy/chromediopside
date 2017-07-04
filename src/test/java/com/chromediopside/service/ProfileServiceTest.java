package com.chromediopside.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.chromediopside.mockbuilder.MockProfileBuilder;
import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.Language;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProfileServiceTest {

  private static final String validAccessToken = System.getenv("TEST_ACCESS_TOKEN");
  private static final String invalidAccessToken = "1nval1dt0k3n";
  private static final String testLogin = System.getenv("TEST_LOGIN");
  private static final String testAvatarUrl = System.getenv("TEST_AVATAR_URL");
  private static final String testRepos = "exam-basics;exam-trial-basics;"
      + "git-lesson-repository;lagopus-spring-exam;p-czigany.github.io";
  private static final Timestamp currentTime = new Timestamp(System.currentTimeMillis());
  private Set<Language> testLanguagesList = new HashSet<>();

  private static final long oneDayInMillis = 86400000;

  @Autowired
  private GiTinderProfile profileToCheck;
  @Autowired
  private ProfileService profileService;
  @Autowired
  private MockProfileBuilder mockProfileBuilder;

  @Before
  public void setup() throws Exception {
    testLanguagesList.add(new Language("Java"));
    testLanguagesList.add(new Language("HTML"));
  }

  @Test
  public void validAccessToken() throws Exception {
    GiTinderProfile expectedProfile = mockProfileBuilder
        .setLogin(testLogin)
        .setAvatarUrl(testAvatarUrl)
        .setRepos(testRepos)
        .setLanguagesList(testLanguagesList)
        .setRefreshDate(currentTime)
        .build();
    GiTinderProfile actualProfile = profileService
        .fetchProfileFromGitHub(validAccessToken, testLogin);
    assertEquals(expectedProfile, actualProfile);
  }

  @Test
  public void invalidAccessToken() throws Exception {
    GiTinderProfile actualProfile = profileService
        .fetchProfileFromGitHub(invalidAccessToken, testLogin);
    assertNull(actualProfile);
  }

  @Test
  public void daysPassedWhenEightHoursPassedBetweenDates() throws Exception {
    Timestamp lastRefresh = new Timestamp(1497441600000l);                 //2017-06-14 14:00:00.0
    Timestamp eightHoursAfterLastRefresh = new Timestamp(1497470400000l);  //2017-06-14 22:00:00.0
    int daysPassed = profileService.daysPassedBetweenDates(lastRefresh, eightHoursAfterLastRefresh);
    assertEquals(0, daysPassed);
  }

  @Test
  public void daysPassedWhenFourDaysPassedBetweenDates() throws Exception {
    Timestamp lastRefresh = new Timestamp(1497441600000l);                 //2017-06-14 14:00:00.0
    Timestamp fourDaysAfterLastRefresh = new Timestamp(1497794400000l);    //2017-06-18 16:00:00.0
    int daysPassed = profileService.daysPassedBetweenDates(lastRefresh, fourDaysAfterLastRefresh);
    assertEquals(4, daysPassed);
  }

  @Test
  public void daysPassedAfter24Hours() throws Exception {
    Timestamp lastRefresh = new Timestamp(1497441600000l);                 //2017-06-14 14:00:00.0
    Timestamp lastRefreshMinusOneDay = new Timestamp(lastRefresh.getTime() - oneDayInMillis);
    int daysPassed = profileService.daysPassedBetweenDates(lastRefresh, lastRefreshMinusOneDay);
    assertEquals(1, daysPassed);
  }

  @Test
  public void refreshRequiredIfZeroHoursPassed() throws Exception {
    profileToCheck.setRefreshDate(new Timestamp(System.currentTimeMillis()));
    assertFalse(profileService.refreshRequired(profileToCheck));
  }

  @Test
  public void refreshRequiredSince1970() throws Exception {
    profileToCheck.setRefreshDate(new Timestamp(00000000000l));
    assertTrue(profileService.refreshRequired(profileToCheck));
  }

  @Test
  public void refreshRequiredSinceNowMinus24Hours() throws Exception {
    profileToCheck.setRefreshDate(new Timestamp(currentTime.getTime() - oneDayInMillis));
    assertTrue(profileService.refreshRequired(profileToCheck));
  }
}
