package com.oxxo.scr.ms.jwt.exceptions;

import com.oxxo.scr.ms.jwt.utils.JwtErrorMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class JwtException extends RuntimeException {

  private final JwtErrorMessage errorMessage;

  private final Integer errorCode;

  public JwtException(final JwtErrorMessage errorMessage) {
    super(errorMessage.getErrorMessage());
    this.errorMessage = errorMessage;
    this.errorCode = errorMessage.getErrorCode();
  }
}
