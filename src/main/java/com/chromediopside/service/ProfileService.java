package com.chromediopside.service;

import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.datatransfer.SwipeResponse;
import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.GiTinderUser;
import com.chromediopside.model.Language;
import com.chromediopside.model.Match;
import com.chromediopside.model.Page;
import com.chromediopside.model.Swiping;
import com.chromediopside.repository.LanguageRepository;
import com.chromediopside.repository.ProfileRepository;
import com.chromediopside.repository.SwipeRepository;
import com.chromediopside.repository.UserRepository;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

  private static final long ONE_DAY_IN_MILLIS = 86400000;

  private UserRepository userRepository;
  private ProfileRepository profileRepository;
  private PageService pageService;
  private GiTinderProfile giTinderProfile;
  private GiTinderUserService giTinderUserService;
  private SwipeRepository swipeRepository;
  private LanguageRepository languageRepository;
  private LogService logService;

  @Autowired
  public ProfileService(
      UserRepository userRepository,
      ProfileRepository profileRepository,
      PageService pageService,
      GiTinderProfile giTinderProfile,
      GiTinderUserService giTinderUserService,
      SwipeRepository swipeRepository,
      LanguageRepository languageRepository,
      LogService logService) {
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
    this.pageService = pageService;
    this.giTinderProfile = giTinderProfile;
    this.giTinderUserService = giTinderUserService;
    this.swipeRepository = swipeRepository;
    this.languageRepository = languageRepository;
    this.logService = logService;
  }

  public ProfileService() {
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
      logService.printLogMessage(GitHubClientService.getGetRequestIoerror());
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
      logService.printLogMessage(GitHubClientService.getGetRequestIoerror());
      return false;
    }
  }

  private boolean fetchCodeFileUrls(GitHubClient gitHubClient, String username,
          GiTinderProfile giTinderProfile) {
    try {
      List<String> fileUrls = new ArrayList<>();
      RepositoryService repositoryService = new RepositoryService(gitHubClient);
      List<Repository> repoList = repositoryService.getRepositories(username);
      CommitService commitService = new CommitService(gitHubClient);
      List<CommitFile> filesInCommit;
      for (Repository repo : repoList) {
        for (RepositoryCommit commit : commitService.getCommits(repo)) {
          filesInCommit = commitService.getCommit(repo, commit.getSha()).getFiles();
          for (CommitFile file : filesInCommit) {
            String fileUrl = file.getRawUrl();
            if (isCodeFile(fileUrl)) {
              fileUrls.add(fileUrl);
            }
          }
        }
      }
      fileUrls = pickRandomFiveListElements(fileUrls);
      String urlsInString = String.join(";", fileUrls);
      giTinderProfile.setRandomCodeLinks(urlsInString);
      return true;
    } catch (IOException e) {
      logService.printLogMessage(GitHubClientService.getGetRequestIoerror());
      return false;
    }
  }

  public <T> List<T> pickRandomFiveListElements(List<T> list) {
    if(list.size() > 5) {
      Collections.shuffle(list);
      return list.subList(0, 5);
    }
    return list;
  }

  public boolean isCodeFile(String fileUrl) {
    String extension = "." + FilenameUtils.getExtension(fileUrl);
    return extensions().contains(extension);
  }

  public List<String> extensions() {
    List<String> extensions = new ArrayList<>();
    for (Language language : languageRepository.findAll()) {
      extensions.add(language.getFileExtension());
    }
    return extensions;
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
    GitHubClient gitHubClient = GitHubClientService.setUpGitHubClient(accessToken);
    GiTinderProfile giTinderProfile = new GiTinderProfile();
    giTinderProfile.setRefreshDate(new Timestamp(System.currentTimeMillis()));
    if (!(setLoginAndAvatar(gitHubClient, username, giTinderProfile))
            || !(setReposAndLanguages(gitHubClient, username, giTinderProfile))
            || !(fetchCodeFileUrls(gitHubClient, username, giTinderProfile))) {
      giTinderProfile = null;
    }
    return giTinderProfile;
  }

  public GiTinderProfile getOtherProfile(String appToken, String username) {

    GiTinderUser giTinderUser = userRepository.findByUserNameAndAppToken(username, appToken);
    if (refreshRequired(profileRepository.findByLogin(giTinderUser.getUserName()))) {
      giTinderProfile = fetchProfileFromGitHub(giTinderUser.getAccessToken(), username);
      profileRepository.save(giTinderProfile);
    }
    giTinderProfile = profileRepository
            .findByLogin(giTinderUser.getUserName());
    return giTinderProfile;
  }

  public GiTinderProfile getOwnProfile(String appToken) {
    return getOtherProfile(appToken, getUserNameByAppToken(appToken));
  }

  public String getUserNameByAppToken(String appToken) {
    return userRepository.findByAppToken(appToken).getUserName();
  }

  public int daysPassedBetweenDates(Timestamp date1, Timestamp date2) {
    long differenceAsLong = 0;
    if (date1.getTime() > date2.getTime()) {
      differenceAsLong = date1.getTime() - date2.getTime();
    } else {
      differenceAsLong = date2.getTime() - date1.getTime();
    }
    return (int)(differenceAsLong / ONE_DAY_IN_MILLIS);
  }

  public boolean refreshRequired(GiTinderProfile profileToCheck) {
    Timestamp currentDate = new Timestamp(System.currentTimeMillis());
    return (daysPassedBetweenDates(profileToCheck.getRefreshDate(), currentDate) >= 1);
  }

  public void fetchAndSaveProfileOnLogin(LoginForm loginForm) {
    if (loginForm.getUsername() == null || loginForm.getAccessToken() == null) {
      return;
    }
    GiTinderProfile currentProfile = fetchProfileFromGitHub(loginForm.getAccessToken(), loginForm.getUsername());
    if (profileRepository.existsByLogin(loginForm.getUsername()) && refreshRequired(profileRepository.findByLogin(loginForm.getUsername())) && currentProfile != null) {
      GiTinderProfile profileToUpdate = profileRepository.findByLogin(loginForm.getUsername());
      profileToUpdate.updateProfile(
          currentProfile.getAvatarUrl(),
          currentProfile.getRepos(),
          currentProfile.getLanguagesList(),
          currentProfile.getRandomCodeLinks());
      profileRepository.save(profileToUpdate);
    }
    if (!profileRepository.existsByLogin(loginForm.getUsername()) && currentProfile != null) {
      profileRepository.save(currentProfile);
    }
  }

  public Page tenProfileByPage(int pageNumber) {
    return pageService.setPage(pageNumber);
  }

  public boolean enoughProfiles(int pageNumber) {
    return profileRepository.count() > ((pageNumber - 1) * PageService.PROFILES_PER_PAGE);
  }

  public SwipeResponse handleSwiping(String appToken, String username, String direction) {
    SwipeResponse swipeResponse = new SwipeResponse();
    if (direction.equals("right")) {
      GiTinderUser swipingUser = giTinderUserService.getUserByAppToken(appToken);
      String swipingUsersName = swipingUser.getUserName();
      if (!swipeRepository.existsBySwipingUsersNameAndSwipedUsersName(username, swipingUsersName)) {

        Swiping swiping = new Swiping(swipingUsersName, username);
        swipeRepository.save(swiping);
      } else {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Swiping matchingSwipe = new Swiping(username, swipingUsersName, timestamp);
        swipeRepository.save(matchingSwipe);
        Match match = new Match(username, timestamp);
        swipeResponse.setMatch(match);
      }
    }
    return swipeResponse;
  }
}
