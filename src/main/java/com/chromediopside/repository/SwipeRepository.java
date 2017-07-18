package com.chromediopside.repository;

import com.chromediopside.model.SwipeId;
import com.chromediopside.model.Swiping;
import java.util.List;
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

  @Query(value = "SELECT * "
          + "FROM swiping "
          + "WHERE timestamp IS NOT NULL "
          + "AND (swiping_users_name = ?1 OR swiped_users_name = ?1)",
          nativeQuery = true)
  List<Swiping> findSwipeMatches(String username);

  @Query(value = "SELECT EXISTS("
          + "SELECT 1 FROM swiping "
          + "WHERE timestamp IS NOT NULL "
          + "AND ((swiping_users_name = ?1 AND swiped_users_name = ?2) "
          + "OR (swiping_users_name = ?2 AND swiped_users_name = ?1)) )",
          nativeQuery = true)
  boolean areTheyMatched(String actualUsersName, String otherUsersName);
}
