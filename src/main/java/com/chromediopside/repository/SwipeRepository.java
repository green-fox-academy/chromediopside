package com.chromediopside.repository;

import com.chromediopside.model.SwipeId;
import com.chromediopside.model.Swiping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SwipeRepository extends CrudRepository<Swiping, SwipeId> {

  Swiping findBySwipingUsersNameAndSwipedUsersName(String swipingUsersName,
          String swipedUsersName);

  @Query(value = "SELECT EXISTS("
          + "SELECT 1 FROM swiping "
          + "WHERE swiping_users_name = ?1 "
          + "AND swiped_users_name = ?2)",
          nativeQuery = true)
  boolean existsBySwipingUsersNameAndSwipedUsersName(String swipingUsersName,
          String swipedUsersName);
}
