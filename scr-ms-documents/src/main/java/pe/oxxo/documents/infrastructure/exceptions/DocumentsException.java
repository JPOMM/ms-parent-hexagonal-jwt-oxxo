package pe.oxxo.documents.infrastructure.exceptions;

import pe.oxxo.documents.infrastructure.utils.OrderErrorMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentsException extends RuntimeException {

  private final OrderErrorMessage errorMessage;

  private final Integer errorCode;

  public DocumentsException(final OrderErrorMessage errorMessage) {
    super(errorMessage.getErrorMessage());
    this.errorMessage = errorMessage;
    this.errorCode = errorMessage.getErrorCode();
  }
}
