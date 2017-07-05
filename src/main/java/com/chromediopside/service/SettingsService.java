package com.chromediopside.service;

import com.chromediopside.model.Settings;
import com.chromediopside.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

  private SettingRepository settingRepository;
  private ProfileService profileService;

  @Autowired
  public SettingsService(
          SettingRepository settingRepository,
          ProfileService profileService) {
    this.settingRepository = settingRepository;
    this.profileService = profileService;
  }


  public Settings getUserSettings(String appToken) {
    String userName = profileService.getUserNameByAppToken(appToken);
    Settings settings = settingRepository.findByLogin(userName);
    return settings;
  }
}
