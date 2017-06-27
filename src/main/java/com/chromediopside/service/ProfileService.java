package com.chromediopside.service;

import com.chromediopside.datatransfer.LoginForm;
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

  private static final long oneDayInMillis = 86400000;
  private static final String GET_REQUEST_IOERROR
          = "Some GitHub data of this user is not available for this token!";

  private UserRepository userRepository;
  private ProfileRepository profileRepository;
  private ErrorService errorService;
  private MockProfileBuilder mockProfileBuilder;
  private PageService pageService;
  private GiTinderProfile giTinderProfile;
  private Language language;
  private GiTinderUserService userService;
  private SwipeRepository swipeRepository;

  @Autowired
  public ProfileService(
          UserRepository userRepository,
          ProfileRepository profileRepository,
          ErrorService errorService,
          PageService pageService,
          MockProfileBuilder mockProfileBuilder,
          GiTinderProfile giTinderProfile,
          Language language,
          GiTinderUserService userService,
          SwipeRepository swipeRepository) {
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
    this.errorService = errorService;
    this.mockProfileBuilder = mockProfileBuilder;
    this.pageService = pageService;
    this.giTinderProfile = giTinderProfile;
    this.language = language;
    this.userService = userService;
    this.swipeRepository = swipeRepository;
  }

  public ProfileService() {
  }

  private GitHubClient setUpGitHubClient(String accessToken) {
    GitHubClient gitHubClient = new GitHubClient();
    gitHubClient.setOAuth2Token(accessToken);
    return gitHubClient;
  }

  private boolean setLoginAndAvatar(GitHubClient gitHubClient, String username,
          GiTinderProfile giTinderProfile) {
    UserService userService = new UserService(gitHubClient);
    try {
      User user = userService.getUser(username);
      giTinderProfile.setLogin(user.getLogin());
      giTinderProfile.setAvatarUrl(user.getAvatarUrl());
      return true;
    } catch (IOException e) {
      System.out.println(GET_REQUEST_IOERROR);
      return false;
    }
  }

  private boolean setReposAndLanguages(GitHubClient gitHubClient, String username,
          GiTinderProfile giTinderProfile) {
    RepositoryService repositoryService = new RepositoryService(gitHubClient);
    try {
      List<Repository> repositoryList = repositoryService.getRepositories(username);
      List<String> repos = new ArrayList<>();
      List<String> languages = new ArrayList<>();
      for (Repository currentRepo : repositoryList) {
        repos.add(currentRepo.getName());
        addRepoLanguage(currentRepo, languages);
      }
      giTinderProfile.setRepos(String.join(";", repos));
      Set<Language> languageObjects = languagesSetFromStringList(languages);
      giTinderProfile.setLanguagesList(languageObjects);
      return true;
    } catch (IOException e) {
      System.out.println(GET_REQUEST_IOERROR);
      return false;
    }
  }

  private void addRepoLanguage(Repository currentRepo, List<String> languages) {
    String repoLanguage = currentRepo.getLanguage();
    if (!languages.contains(repoLanguage)) {
      languages.add(repoLanguage);
    }
  }

  private Set<Language> languagesSetFromStringList(List<String> languages) {
    Set<Language> languageObjects = new HashSet<>();
    for (String currentLanguage : languages) {
      languageObjects.add(new Language(currentLanguage));
    }
    return languageObjects;
  }

  public GiTinderProfile fetchProfileFromGitHub(String accessToken, String username) {
    GitHubClient gitHubClient = setUpGitHubClient(accessToken);
    GiTinderProfile giTinderProfile = new GiTinderProfile();
    giTinderProfile.setRefreshDate(new Timestamp(System.currentTimeMillis()));
    if (!(setLoginAndAvatar(gitHubClient, username, giTinderProfile) &&
            setReposAndLanguages(gitHubClient, username, giTinderProfile))) {
      giTinderProfile = null;
    }
    return giTinderProfile;
  }

  public ResponseEntity<?> getOtherProfile(String appToken, String username) {
    if (appToken == null || userRepository.findByAppToken(appToken) == null) {
      return errorService.unauthorizedRequestError();
    }
    if (userRepository.findByUserName(username) == null) {
      return errorService.noSuchUserError();
    }
    GiTinderUser authenticatedUser = userRepository.findByUserNameAndAppToken(username, appToken);
    if (profileRepository.existsByLogin(authenticatedUser.getUserName()) || refreshRequired(
            profileRepository
                    .findByLogin(authenticatedUser.getUserName()))) {
      profileRepository.save(fetchProfileFromGitHub(authenticatedUser.getAccessToken(), username));
    }
    GiTinderProfile upToDateProfile = profileRepository
            .findByLogin(authenticatedUser.getUserName());
    return new ResponseEntity<Object>(upToDateProfile, HttpStatus.OK);
  }

  public ResponseEntity<?> getOwnProfile(String appToken) {
    if (!appToken.equals("")) {
      GiTinderProfile mockProfile = mockProfileBuilder.build();
      return new ResponseEntity<Object>(mockProfile, HttpStatus.OK);
    }
    return errorService.unauthorizedRequestError();
  }

  public int daysPassedBetweenDates(Timestamp date1, Timestamp date2) {
    long differenceAsLong = 0;
    if (date1.getTime() > date2.getTime()) {
      differenceAsLong = date1.getTime() - date2.getTime();
    } else {
      differenceAsLong = date2.getTime() - date1.getTime();
    }
    int differenceAsDays = (int) (differenceAsLong / oneDayInMillis);
    return differenceAsDays;
  }

  public boolean refreshRequired(GiTinderProfile profileToCheck) {
    Timestamp currentDate = new Timestamp(System.currentTimeMillis());
    if (daysPassedBetweenDates(profileToCheck.getRefreshDate(), currentDate) >= 1) {
      return true;
    }
    return false;
  }

  public void fetchAndSaveProfileOnLogin(LoginForm loginForm) {
    if (loginForm.getUsername() != null && loginForm.getAccessToken() != null) {
      GiTinderProfile currentProfile = fetchProfileFromGitHub(loginForm.getAccessToken(),
              loginForm.getUsername());
      if (profileRepository.existsByLogin(loginForm.getUsername()) && currentProfile != null) {
        GiTinderProfile profileToCheck = profileRepository.findByLogin(loginForm.getUsername());
        if (refreshRequired(profileToCheck)) {
          profileToCheck.updateProfile(currentProfile.getAvatarUrl(),
                  currentProfile.getRepos(),
                  currentProfile.getLanguagesList(),
                  new Timestamp(System.currentTimeMillis()));
        }
      } else {
        if (currentProfile != null) {
          profileRepository.save(currentProfile);
        }
      }
    }
  }

  public ResponseEntity<?> tenProfileByPage(String appToken, int pageNumber) {
    if (validAppToken(appToken)) {
      if (enoughProfiles(pageNumber)) {
        return new ResponseEntity<Object>(pageService.setPage(pageNumber), HttpStatus.OK);
      }
      return errorService.getNoMoreAvailableProfiles();
    } else {
      return errorService.unauthorizedRequestError();
    }
  }

  private boolean enoughProfiles(int pageNumber) {
    return profileRepository.count() > ((pageNumber - 1) * PageService.PROFILES_PER_PAGE);
  }

  private boolean validAppToken(String appToken) {
    return userRepository.findByAppToken(appToken) == null;
  }

  public ResponseEntity<?> handleSwiping(String appToken, String username, String direction) {
    GiTinderUser swipingUser = userService.getUserByAppToken(appToken);
    String swipingUsersName = swipingUser.getUserName();

    if (direction.equals("right")) {
      Swiping swiping = new Swiping(swipingUsersName, username);
      swipeRepository.save(swiping);
    }

    boolean match_status = false;
    if (swipeRepository.existsBySwipingUsersNameAndSwipedUsersName(username, swipingUsersName)) {
      match_status = true;
    }

    SwipeResponse swipeResponse = new SwipeResponse(match_status);
    ResponseEntity<?> responseEntity = new ResponseEntity<Object>(swipeResponse, HttpStatus.OK);
    return responseEntity;
  }
}
