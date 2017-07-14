package com.chromediopside.mockbuilder;

import com.chromediopside.model.Language;
import com.chromediopside.model.Settings;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class MockSettingsBuilder {

  private Settings settings;

  public MockSettingsBuilder() {
    Set<Language> emptyLanguageSet =null;
    settings = new Settings(true, true, 50, emptyLanguageSet);
  }

  public MockSettingsBuilder setEnableNotifications(boolean enableNotifications) {
    settings.setEnableNotifications(enableNotifications);
    return this;
  }

  public MockSettingsBuilder setEnableBackgroundSync(boolean enableBackgroundSync) {
    settings.setEnableBackgroundSync(enableBackgroundSync);
    return this;
  }

  public MockSettingsBuilder setMaxDistance(int maxDistance) {
    settings.setMaxDistance(maxDistance);
    return this;
  }

  public MockSettingsBuilder setPreferredLanguagesList(Set<Language> preferredLanguagesList) {
    settings.setPreferredLanguages(preferredLanguagesList);
    return this;
  }

  public Settings build() {
    return settings;
  }

  public Settings getProfile() {
    return settings;
  }
}

