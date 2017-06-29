package com.chromediopside.model;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class SwipeId implements Serializable {
  private String swipingUsersName;
  private String swipedUsersName;

}
