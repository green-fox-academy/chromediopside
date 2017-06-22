package com.chromediopside.service;

import com.chromediopside.datatransfer.ErrorResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ErrorService {

  private static final String MISSING_PARAMS_MESSAGE = "Missing parameter(s): ";
  private ErrorResponse errorResponse;

  @Autowired
  public ErrorService(ErrorResponse errorResponse) {
    this.errorResponse = errorResponse;
  }

  public ResponseEntity<?> fieldErrors(BindingResult bindingResult) {
    errorResponse.setStatus("error");
    errorResponse.setMessage(MISSING_PARAMS_MESSAGE + missingValues(bindingResult));
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  private String missingValues(BindingResult bindingResult) {
    List<String> missingFields = new ArrayList<>();
    for (FieldError currentFieldError : bindingResult.getFieldErrors()) {
      missingFields.add(currentFieldError.getField());
    }
    Collections.sort(missingFields);
    return String.join(", ", missingFields) + "!";
  }

  public ResponseEntity<?> getResponseEntity(String message, HttpStatus httpStatus) {
    errorResponse.setMessage(message);
    return new ResponseEntity<>(errorResponse, httpStatus);
  }

  public ResponseEntity<?> getUnauthorizedResponseEntity() {
    return getResponseEntity("Unauthorized request!", HttpStatus.UNAUTHORIZED);
  }
}
