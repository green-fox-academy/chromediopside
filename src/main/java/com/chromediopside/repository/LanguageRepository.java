package com.chromediopside.repository;


import com.chromediopside.model.Language;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<Language, Long> {

  List<Language> findAllByLanguageName(String languageName);

  Language findByLoginAndLanguageName(String login, String languageName);

}
