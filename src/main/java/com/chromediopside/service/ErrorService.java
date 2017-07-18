package com.chromediopside.service;

import com.chromediopside.datatransfer.ErrorResponse;
import com.chromediopside.datatransfer.LoginForm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrorService {

  private static final String UNAUTHORIZED_REQUEST_MESSAGE = "Unauthorized request!";
  private static final String NO_SUCH_USER_MESSAGE = "No such user!";
  private static final String MISSING_PARAMS_MESSAGE = "Missing parameter(s): ";
  private static final String NO_MORE_PROFILES_AVAILABLE_ERROR = "No more available profiles for you!";
  private static final String ERROR_STATUS = "error";

  private ErrorResponse errorResponse;

  @Autowired
  public ErrorService(ErrorResponse errorResponse) {
    this.errorResponse = errorResponse;
  }

  String missingValues(LoginForm loginForm) {
    List<String> missingFields = new ArrayList<>();
    if (loginForm.getUsername() == null) {
      missingFields.add("username");
    }
    if (loginForm.getAccessToken() == null) {
      missingFields.add("accessToken");

    }
    Collections.sort(missingFields);
    return String.join(", ", missingFields);
  }

  public ErrorResponse fieldErrors(LoginForm loginForm) {
    errorResponse.setStatus(ERROR_STATUS);
    errorResponse.setMessage(MISSING_PARAMS_MESSAGE + missingValues(loginForm) + "!");
    return errorResponse;
  }

  public ErrorResponse unauthorizedRequestError() {
    errorResponse.setStatus(ERROR_STATUS);
    errorResponse.setMessage(UNAUTHORIZED_REQUEST_MESSAGE);
    return errorResponse;
  }

  public ErrorResponse noSuchUserError() {
    errorResponse.setStatus(ERROR_STATUS);
    errorResponse.setMessage(NO_SUCH_USER_MESSAGE);
    return errorResponse;
  }

  public ErrorResponse getNoMoreAvailableProfiles() {
    errorResponse.setStatus("ok");
    errorResponse.setMessage(NO_MORE_PROFILES_AVAILABLE_ERROR);
    return errorResponse;
  }
}
