package com.chromediopside.service;

import com.chromediopside.datatransfer.SwipeResponse;
import com.chromediopside.mockbuilder.MockProfileBuilder;
import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.GiTinderUser;
import com.chromediopside.model.Language;
import com.chromediopside.model.Swiping;
import com.chromediopside.repository.ProfileRepository;
import com.chromediopside.repository.SwipeRepository;
import com.chromediopside.repository.UserRepository;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.geometry.HorizontalDirection;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

  private static final String GET_REQUEST_IOERROR
      = "Some GitHub data is not available for this accessToken!";

  private UserRepository userRepository;
  private ProfileRepository profileRepository;
  private ErrorService errorService;
  private MockProfileBuilder mockProfileBuilder;
  private GiTinderProfile giTinderProfile;
  private Language language;
  private GiTinderUserService userService;
  private SwipeRepository swipeRepository;

  @Autowired
  public ProfileService(
      UserRepository userRepository,
      ProfileRepository profileRepository,
      ErrorService errorService,
      MockProfileBuilder mockProfileBuilder,
      GiTinderProfile giTinderProfile,
      Language language,
      GiTinderUserService userService,
          SwipeRepository swipeRepository) {
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
    this.errorService = errorService;
    this.mockProfileBuilder = mockProfileBuilder;
    this.giTinderProfile = giTinderProfile;
    this.language = language;
    this.userService = userService;
    this.swipeRepository = swipeRepository;
  }

  public ProfileService() {
  }

  public List<GiTinderProfile> randomTenProfileByLanguage(String languageName) {
    return profileRepository.selectTenRandomLanguageName(languageName);
  }

  public GiTinderProfile getProfileFromGitHub(String accessToken) {
    GiTinderProfile giTinderProfile = new GiTinderProfile();

    GitHubClient gitHubClient = new GitHubClient();
    gitHubClient.setOAuth2Token(accessToken);

    UserService userService = new UserService(gitHubClient);
    RepositoryService repositoryService = new RepositoryService(gitHubClient);
    try {
      User user = userService.getUser();
      giTinderProfile.setLogin(user.getLogin());
      giTinderProfile.setAvatarUrl(user.getAvatarUrl());
      List<Repository> repositoryList = repositoryService.getRepositories();
      List<String> repos = new ArrayList<>();
      List<String> languages = new ArrayList<>();
      for (Repository currentRepo : repositoryList) {
        repos.add(currentRepo.getName());
        String repoLanguage = currentRepo.getLanguage();
        if (!languages.contains(repoLanguage)) {
          languages.add(repoLanguage);
        }
      }
      giTinderProfile.setRepos(String.join(";", repos));
      Set<Language> languageObjects = new HashSet<>();
      for (String currentLanguage : languages) {
        languageObjects.add(new Language(currentLanguage));
      }
      giTinderProfile.setLanguagesList(languageObjects);
      return giTinderProfile;
    } catch (IOException e) {
      System.out.println(GET_REQUEST_IOERROR);
      return null;
    }
  }

  public ResponseEntity<?> profile(String username, String appToken) {
    if (userRepository.findByUserName(username) == null) {
      return errorService.noSuchUserError();
    }
    if (appToken == null || userRepository.findByUserNameAndAppToken(username, appToken) == null) {
      return errorService.unauthorizedRequestError();
    }
    GiTinderUser authenticatedUser = userRepository.findByUserNameAndAppToken(username, appToken);
    if (profileRepository.findByLogin(authenticatedUser.getUserName()) == null || refreshRequired(
        profileRepository
            .findByLogin(authenticatedUser.getUserName()))) {
      profileRepository.save(getProfileFromGitHub(authenticatedUser.getAccessToken()));
    }
    GiTinderProfile upToDateProfile = profileRepository
        .findByLogin(authenticatedUser.getUserName());
    return new ResponseEntity<Object>(upToDateProfile, HttpStatus.OK);
  }

  public ResponseEntity<?> getProfile(String appToken) {
    if (!appToken.equals("")) {
      GiTinderProfile mockProfile = mockProfileBuilder.build();
      return new ResponseEntity<Object>(mockProfile, HttpStatus.OK);
    }
    return errorService.unauthorizedRequestError();
  }

  public int daysPassedSinceLastRefresh(GiTinderProfile profileToCheck) {
    Timestamp currentDate = new Timestamp(System.currentTimeMillis());
    Timestamp lastRefresh = profileToCheck.getRefreshDate();
    long differenceAsLong = currentDate.getTime() - lastRefresh.getTime();
    int differenceAsDays = (int) (differenceAsLong / (1000 * 60 * 60 * 24));
    return differenceAsDays;
  }

  public boolean refreshRequired(GiTinderProfile profileToCheck) {
    if (daysPassedSinceLastRefresh(profileToCheck) >= 1) {
      return true;
    }
    return false;
  }

  public ResponseEntity<?> handleSwiping(String appToken, String username, String direction,
          HorizontalDirection horizontalDirection) {

    GiTinderUser swipingUser = userService.getUserByAppToken(appToken);
    String swipingUsersName = swipingUser.getUserName();
    String upperCaseDirection = direction.toUpperCase();
    Swiping swiping = new Swiping
            (swipingUsersName, username, horizontalDirection.valueOf(upperCaseDirection));
    swipeRepository.save(swiping);

    boolean match_status = false;
    if (direction.equals("right")
            && !swipeRepository.findBySwipingUsersNameAndSwipedUsersNameAndSwipeDirection
            (username, swipingUsersName, horizontalDirection.valueOf("RIGHT"))
            .equals(null)) {
      match_status = true;
    }

    SwipeResponse swipeResponse = new SwipeResponse(match_status);
    ResponseEntity<?> responseEntity = new ResponseEntity<Object>(swipeResponse, HttpStatus.OK);
    return responseEntity;
  }
}
