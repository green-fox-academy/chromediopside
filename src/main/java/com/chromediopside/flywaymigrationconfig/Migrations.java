package com.chromediopside.flywaymigrationconfig;

import org.flywaydb.core.Flyway;

public class Migrations {

  public static void main(String[] args) {
    Flyway flyway = new Flyway();
    flyway.setDataSource(System.getenv("SPRING_DATASOURCE_URL"),
        System.getenv("SPRING_DATASOURCE_USERNAME"),
        System.getenv("SPRING_DATASOURCE_PASSWORD"));
    flyway.migrate();
  }
}
