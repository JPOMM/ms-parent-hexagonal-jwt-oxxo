package com.oxxo.scr.ms.maintance.utils.errorhandler;

import com.oxxo.scr.ms.maintance.exceptions.MaintenanceException;
import com.oxxo.scr.ms.common.utils.GenericResponse;
import com.oxxo.scr.ms.common.utils.GenericResponseConstants;
import com.oxxo.scr.ms.maintance.utils.MaintenanceErrorMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

  public record ErrorResponse(Integer code, String message) {

  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public GenericResponse<Object> exception(final Exception ex) {
    return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR, GenericResponseConstants.WRONG_OPERATION, ex.getMessage());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(code = HttpStatus.CONFLICT)
  public GenericResponse<Object> dataIntegrityViolationException(final DataIntegrityViolationException ex) {
    return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR, GenericResponseConstants.WRONG_OPERATION, GenericResponseConstants.CONFLICT);
  }

  @ExceptionHandler(MaintenanceException.class)
  public ResponseEntity<GenericResponse<Object>> handleMaintenanceException(MaintenanceException ex) {
    MaintenanceErrorMessage errorMessage = ex.getErrorMessage();
    HttpStatus status = switch (errorMessage) {
      case ERROR_NOT_FOUND_IMAGE -> HttpStatus.BAD_REQUEST;
      case ERROR_SAVING_IMAGE, ERROR_UPDATE_IMAGE, ERROR_DELETE_IMAGE, ERROR_SECURITY_GOOGLE_DRIVE, ERROR_RESOURCE_NOT_FOUND,
           ERROR_INTERNAL -> HttpStatus.INTERNAL_SERVER_ERROR;
    };
    return new ResponseEntity<>(new GenericResponse<>(GenericResponseConstants.RPTA_ERROR, GenericResponseConstants.WRONG_OPERATION,
        new ErrorResponse(ex.getErrorCode(), errorMessage.getErrorMessage())), status);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public GenericResponse<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex) {
    return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR,
        StringUtils.joinWith(GenericResponseConstants.COLON, GenericResponseConstants.WRONG_OPERATION, ex.getRequestURL()),
        ex.getMessage());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
  public GenericResponse<Object> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
    return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR,
        StringUtils.joinWith(GenericResponseConstants.COLON, GenericResponseConstants.WRONG_OPERATION, "The method is not allowed"),
        ex.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public GenericResponse<Object> constraintValidations(final ConstraintViolationException ex) {
    final Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
    final List<String> errorMessages = violations.stream()
        .map(ConstraintViolation::getMessage)
        .toList();
    final Map<String, List<String>> listHashMap = Collections.singletonMap("violations", errorMessages);
    return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR, GenericResponseConstants.WRONG_OPERATION, listHashMap);
  }
}
