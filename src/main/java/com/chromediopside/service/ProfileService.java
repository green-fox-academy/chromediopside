package com.chromediopside.service;

import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.Language;
import com.chromediopside.repository.LanguageRepository;
import com.chromediopside.repository.ProfileRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfileService {

  @Autowired
  ProfileRepository profileRepository;
  @Autowired
  LanguageRepository languageRepository;

  public GiTinderProfile fetchProfileByLogin(String login) {
    return profileRepository.findByLogin(login);
  }

  public List<GiTinderProfile> randomTenProfileByLanguage(String languageName) {
    List<Language> profilesByLanguage = languageRepository.findAllByLanguageName(languageName);
    List<GiTinderProfile> randomTenProfileByLanguage = new ArrayList<>();

    if (profilesByLanguage.size() > 10) {
      Collections.shuffle(profilesByLanguage);
      profilesByLanguage = profilesByLanguage.subList(0, 10);
    }

    for (Language languageCurrent : profilesByLanguage) {
      randomTenProfileByLanguage.add(profileRepository.findByLogin(languageCurrent.getLogin()));
    }
    return randomTenProfileByLanguage;
  }


  public GiTinderProfile getGithubProfileData(String username) throws JSONException, IOException {
    String githubDataApi = "https://api.github.com/users/" + username + "/repos";

    JSONArray jsonArray = readJsonFromUrl(githubDataApi);
    GiTinderProfile giTinderProfile = new GiTinderProfile();
    List<String> repos = new ArrayList<>();
    List<Language> languageList = new ArrayList<>();
    Set<String> stringSet = new HashSet<>();
    Set<Language> languageSet = new HashSet<Language>(languageList);

    giTinderProfile.setLogin(username);
    System.out.println(giTinderProfile.getLogin());
    giTinderProfile.setAvatarUrl(
            jsonArray.getJSONObject(0).getJSONObject("owner").getString("avatar_url"));
    System.out.println(giTinderProfile.getAvatarUrl());

    for (int i = 0; i < jsonArray.length(); i++) {
      repos.add(jsonArray.getJSONObject(i).getString("name"));
      String language = jsonArray.getJSONObject(i).getString("language");
      languageList.add(new Language(language, username));

      for (Language languageElement : languageList) {
        stringSet.add(languageElement.getLanguageName());
      }

      for (String string : stringSet) {
        languageList.clear();
        languageList.add(new Language(string, username));
        languageSet.addAll(languageList);
      }
    }

    giTinderProfile.setRepos(String.join("; ", repos));
    System.out.println(giTinderProfile.getRepos());

    giTinderProfile.setLanguagesList(languageSet);
    System.out.println(giTinderProfile.getLanguagesList());

    return giTinderProfile;
  }


  private static String readAll(Reader reader) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    int cp;
    while ((cp = reader.read()) != -1) {
      stringBuilder.append((char) cp);
    }
    return stringBuilder.toString();
  }


  private static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream inputStream = new URL(url).openStream();
    try {
      BufferedReader bufferedReader = new BufferedReader(
              new InputStreamReader(inputStream, Charset.forName("UTF-8")));
      String jsonText = readAll(bufferedReader);
      JSONArray json = new JSONArray(jsonText);
      return json;
    } finally {
      inputStream.close();
    }
  }

}
