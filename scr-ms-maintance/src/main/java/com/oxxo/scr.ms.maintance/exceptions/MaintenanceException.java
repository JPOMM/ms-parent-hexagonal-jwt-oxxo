package com.oxxo.scr.ms.maintance.exceptions;

import com.oxxo.scr.ms.maintance.utils.MaintenanceErrorMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MaintenanceException extends RuntimeException {

  private final MaintenanceErrorMessage errorMessage;

  private final Integer errorCode;

  public MaintenanceException(final MaintenanceErrorMessage errorMessage) {
    super(errorMessage.getErrorMessage());
    this.errorMessage = errorMessage;
    this.errorCode = errorMessage.getErrorCode();
  }
}
