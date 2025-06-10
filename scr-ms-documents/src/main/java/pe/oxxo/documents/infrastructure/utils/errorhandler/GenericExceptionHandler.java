package pe.oxxo.documents.infrastructure.utils.errorhandler;

import pe.oxxo.documents.infrastructure.exceptions.DocumentsException;

import com.oxxo.scr.ms.common.utils.GenericResponse;
import com.oxxo.scr.ms.common.utils.GenericResponseConstants;
import pe.oxxo.documents.infrastructure.utils.OrderErrorMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GenericExceptionHandler {

  public record ErrorResponse(Integer code, String message) {

  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public GenericResponse<Object> genericException(final Exception ex) {
    return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR, GenericResponseConstants.WRONG_OPERATION, ex.getMessage());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(code = HttpStatus.CONFLICT)
  public GenericResponse<Object> dataIntegrityViolationException(final DataIntegrityViolationException ex) {
    return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR, GenericResponseConstants.WRONG_OPERATION,
        GenericResponseConstants.CONFLICT);
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

  @ExceptionHandler(DocumentsException.class)
  public ResponseEntity<GenericResponse<Object>> handleOrderException(final DocumentsException ex) {
    OrderErrorMessage errorMessage = ex.getErrorMessage();
    HttpStatus status = switch (errorMessage) {
      case ERROR_RESOURCE_STATUS_NOT_AVAILABLE, ERROR_RESOURCE_ORDER_NOT_AVAILABLE -> HttpStatus.NOT_FOUND;
      case SERVICE_PRODUCTS_NOT_AVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
      case ERROR_INTERNAL -> HttpStatus.INTERNAL_SERVER_ERROR;
      case INVALID_TOKEN -> HttpStatus.UNAUTHORIZED;
    };
    return new ResponseEntity<>(new GenericResponse<>(GenericResponseConstants.RPTA_ERROR, GenericResponseConstants.WRONG_OPERATION,
        new ErrorResponse(ex.getErrorCode(), errorMessage.getErrorMessage())), status);
  }
}
