package com.chromediopside.service;

import com.chromediopside.mockbuilder.MockProfileBuilder;
import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.Language;
import com.chromediopside.repository.ProfileRepository;
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

  private static final String GET_REQUEST_IOERROR
      = "Some GitHub data is not available for this accessToken!";
  private ProfileRepository profileRepository;
  private ErrorService errorService;
  private MockProfileBuilder mockProfileBuilder;

  @Autowired
  public ProfileService(ProfileRepository profileRepository, ErrorService errorService,
      MockProfileBuilder mockProfileBuilder) {
    this.profileRepository = profileRepository;
    this.errorService = errorService;
    this.mockProfileBuilder = mockProfileBuilder;
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

  public ResponseEntity<?> getProfile(String accessToken) {
    if (!accessToken.equals("")) {
      GiTinderProfile mockProfile = mockProfileBuilder.build();
      return new ResponseEntity<Object>(mockProfile, HttpStatus.OK);
    }
    return errorService.getUnauthorizedResponseEntity();
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
}
