package com.chromediopside.service;

import java.sql.Timestamp;
import org.springframework.stereotype.Service;

@Service
public class LogService {

  private Timestamp currentTime = new Timestamp(System.currentTimeMillis());
  private String logLevel = System.getenv("LOG_LEVEL");

  public void printLogMessage(String logType, String logMessage) {
    String messageToPrint = currentTime + " " + logType + ": " + logMessage;
    if (logType.equals("ERROR")) {
      errorLog(messageToPrint);
    }
    if (logLevel.equals("INFO") && logType.equals("INFO")) {
      infoLog(messageToPrint);
    }
  }

  private void errorLog(String logMessage) {
    System.err.println(logMessage);
  }

  private void infoLog(String logMessage) {
    System.out.println(logMessage);
  }
}
