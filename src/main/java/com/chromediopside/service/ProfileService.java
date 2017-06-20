package com.chromediopside.service;

import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.Language;
import com.chromediopside.repository.LanguageRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfileService {

  @Autowired
  private LanguageRepository languageRepository;

  private static final String GET_REQUEST_IOERROR = "Some GitHub data not available for this accessToken!";

  public List<GiTinderProfile> randomTenProfileByLanguage(String languageName) {
    Language selectingLanguage = languageRepository.findOne(languageName);
    Set<GiTinderProfile> profilesSet = selectingLanguage.getProfileSet();
    List<GiTinderProfile> profileList = new ArrayList<>();
    profileList.addAll(profilesSet);

    if (profileList.size() > 10) {
      Collections.shuffle(profileList);
      profileList.subList(0, 10);
    }

    return profileList;
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
      for (Repository currentRepo :
          repositoryList) {
        repos.add(currentRepo.getName());
        String repoLanguage = currentRepo.getLanguage();
        if (!languages.contains(repoLanguage)) {
          languages.add(repoLanguage);
        }
      }
      giTinderProfile.setRepos(String.join(";", repos));
      Set<Language> languageObjects = new HashSet<>();
      for (String currentLanguage :
          languages) {
        languageObjects.add(new Language(currentLanguage));
      }
      giTinderProfile.setLanguagesList(languageObjects);
      return giTinderProfile;
    } catch (IOException e) {
      System.out.println(GET_REQUEST_IOERROR);
      return null;
    }
  }

}
