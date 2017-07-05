package com.chromediopside.repository;

import com.chromediopside.model.Settings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends CrudRepository<Settings, Long> {

  Settings findByLogin(String login);

}
