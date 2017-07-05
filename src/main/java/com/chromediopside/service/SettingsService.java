package com.chromediopside.service;

import com.chromediopside.model.Language;
import com.chromediopside.model.Settings;
import com.chromediopside.repository.LanguageRepository;
import com.chromediopside.repository.SettingRepository;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

  private SettingRepository settingRepository;
  private ProfileService profileService;
  private LanguageRepository languageRepository;

  @Autowired
  public SettingsService(
          SettingRepository settingRepository,
          ProfileService profileService,
          LanguageRepository languageRepository) {
    this.settingRepository = settingRepository;
    this.profileService = profileService;
    this.languageRepository = languageRepository;
  }


  public Settings getUserSettings(String appToken) {
    String userName = profileService.getUserNameByAppToken(appToken);
    Settings settings = settingRepository.findByLogin(userName);
    return settings;

  }

  public Settings updateSettings(String appToken, JSONObject jsonObject) throws JSONException {
    Settings settings = getUserSettings(appToken);
    if (jsonObject.getJSONObject("enable_notifications") != null) {
      settings.setEnableNotifications(jsonObject.getBoolean("enable_notifications"));
    }
    if (jsonObject.getJSONObject("enable_background_sync") != null) {
      settings.setEnableBackgroundSync(jsonObject.getBoolean("enable_background_sync"));
    }
    if (jsonObject.getJSONObject("max_distance") != null) {
      settings.setMaxDistance(jsonObject.getInt("max_distance"));
    }
    if (jsonObject.getJSONArray("preferred_languages") != null) {
      Set<Language> listOfLanguages = new HashSet<>();
      JSONArray jsonArray = jsonObject.getJSONArray("preferred_languages");
      for (int i = 0; i < jsonArray.length(); i++) {
        Language language = languageRepository.findOne(jsonArray.getString(i));
        listOfLanguages.add(language);
      }
        settings.setPreferredLanguages(listOfLanguages);
    }
    return settings;
  }
}
