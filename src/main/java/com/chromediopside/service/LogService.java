package com.chromediopside.service;

import java.sql.Timestamp;
import org.springframework.stereotype.Service;

@Service
public class LogService {

  private Timestamp currentTime = new Timestamp(System.currentTimeMillis());
  private String logLevel = System.getenv("LOG_LEVEL");

  public void printLogMessage(String logMessage) {
    String messageToPrint = currentTime + " " + logLevel + ": " + logMessage;
    if (logLevel.equals("INFO")) {
      infoLog(messageToPrint);
    } else {
      errorLog(messageToPrint);
    }
  }

  private void errorLog(String logMessage) {
    System.err.println(logMessage);
  }

  private void infoLog(String logMessage) {
    System.out.println(logMessage);
  }
}
