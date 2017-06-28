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

  public String getSwipingUsersName() {
    return swipingUsersName;
  }

  public String getSwipedUsersName() {
    return swipedUsersName;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setSwipingUsersName(String swipingUsersName) {
    this.swipingUsersName = swipingUsersName;
  }

  public void setSwipedUsersName(String swipedUsersName) {
    this.swipedUsersName = swipedUsersName;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }
}
