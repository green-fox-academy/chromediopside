package com.chromediopside.service;

import com.chromediopside.model.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

  private Settings settings;

  @Autowired
  public SettingsService(Settings settings) {
    this.settings = settings;
  }


  public Settings getUserSettings(String appToken) {


    return new Settings();
  }
}
