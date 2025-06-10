package com.oxxo.scr.ms.common.exception;

import com.oxxo.scr.ms.common.utils.GenericErrorMessage;
import lombok.Getter;

@Getter
public class GenericException extends RuntimeException {

  private final GenericErrorMessage errorMessage;

  private final Integer code;

  public GenericException(final GenericErrorMessage errorMessage) {
    super(errorMessage.getMessage());
    this.errorMessage = errorMessage;
    this.code = errorMessage.getCode();
  }
}
