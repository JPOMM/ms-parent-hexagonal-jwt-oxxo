package pe.oxxo.documents.infrastructure.utils;

import jakarta.validation.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrdersUtils {

  /**
   * Méthod for valid the object request dto.
   *
   * @param object the object.
   */
  public static <T> void validRequestDto(final T object) {
    final Validator validator;
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      validator = factory.getValidator();
    }
    final Set<ConstraintViolation<Object>> violations = validator.validate(object);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
