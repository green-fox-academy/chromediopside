package com.chromediopside.service;

import com.chromediopside.datatransfer.ErrorResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ErrorService {

  private static final String UNAUTHORIZED_REQUEST_MESSAGE = "Unauthorized request!";
  private static final String NO_SUCH_USER_MESSAGE = "No such user!";
  private static final String MISSING_PARAMS_MESSAGE = "Missing parameter(s): ";
  private static final String NO_MORE_PROFILES_AVAILABLE_ERROR = "No more available profiles for you!";
  private ErrorResponse errorResponse;

  @Autowired
  public ErrorService(ErrorResponse errorResponse) {
    this.errorResponse = errorResponse;
  }

  private String missingValues(BindingResult bindingResult) {
    List<String> missingFields = new ArrayList<>();
    for (FieldError currentFieldError : bindingResult.getFieldErrors()) {
      missingFields.add(currentFieldError.getField());
    }
    Collections.sort(missingFields);
    return String.join(", ", missingFields) + "!";
  }

  public ErrorResponse fieldErrors(BindingResult bindingResult) {
    errorResponse.setStatus("error");
    errorResponse.setMessage(MISSING_PARAMS_MESSAGE + missingValues(bindingResult));
    return errorResponse;
  }

  public ErrorResponse unauthorizedRequestError() {
    errorResponse.setStatus("error");
    errorResponse.setMessage(UNAUTHORIZED_REQUEST_MESSAGE);
    return errorResponse;
  }

  public ErrorResponse noSuchUserError() {
    errorResponse.setStatus("error");
    errorResponse.setMessage(NO_SUCH_USER_MESSAGE);
    return errorResponse;
  }

  public ErrorResponse getNoMoreAvailableProfiles() {
    errorResponse.setStatus("ok");
    errorResponse.setMessage(NO_MORE_PROFILES_AVAILABLE_ERROR);
    return errorResponse;
  }
}
