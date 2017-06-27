package com.chromediopside.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(SwipeId.class)
public class Swiping {

  @Id
  private String swipingUsersName;
  @Id
  private String swipedUsersName;

  private Timestamp timestamp;

  public Swiping() {
  }

  public Swiping(String swipingUsersName, String swipedUsersName) {
    this.swipingUsersName = swipingUsersName;
    this.swipedUsersName = swipedUsersName;
  }

  public Swiping(String swipingUsersName, String swipedUsersName, Timestamp timestamp) {
      this.swipingUsersName = swipingUsersName;
      this.swipedUsersName = swipedUsersName;
      this.timestamp = timestamp;
    }
}
