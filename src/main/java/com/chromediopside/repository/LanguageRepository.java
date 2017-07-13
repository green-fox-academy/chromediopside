package com.chromediopside.repository;

import com.chromediopside.model.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends CrudRepository<Language, String> {

  boolean existsByFileExtension(String fileExtension);
}
