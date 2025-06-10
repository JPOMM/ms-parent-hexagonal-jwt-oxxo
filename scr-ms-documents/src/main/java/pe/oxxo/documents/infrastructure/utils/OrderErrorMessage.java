package pe.oxxo.documents.infrastructure.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderErrorMessage {
  INVALID_TOKEN(401, "The token is invalid or has been modified"),
  ERROR_RESOURCE_STATUS_NOT_AVAILABLE(404, "The order status ID does not exist in the database"),
  ERROR_RESOURCE_ORDER_NOT_AVAILABLE(404, "The order ID does not exist in the database"),
  ERROR_INTERNAL(500, "Internal Server Error"),
  SERVICE_PRODUCTS_NOT_AVAILABLE(503, "The product stock could not be verified, the service is not available");

  private final Integer errorCode;

  private final String errorMessage;
}
