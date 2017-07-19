package com.chromediopside.repository;

import com.chromediopside.model.Message;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

  List<Message> findByFromInAndToIn(String from, String to);

  @Query(value = "SELECT * "
          + "FROM message "
          + "WHERE (\"from\" = ?1 AND \"to\" = ?2)"
          + "OR (\"from\" = ?2 AND \"to\" = ?1) "
          + "ORDER BY created_at ASC",
          nativeQuery = true)
  List<Message> findMessagesBetween(String username1, String username2);
}
