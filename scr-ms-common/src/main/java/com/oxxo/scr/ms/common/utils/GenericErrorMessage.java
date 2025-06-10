package com.oxxo.scr.ms.common.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GenericErrorMessage {
  INVALID_REQUEST_BODY(105, "The body of the request is not valid, check the fields length and try again"),
  DATABASE_SAVE_ERROR(106, "An error occurred while saving to the database."),
  DATABASE_UPDATE_ERROR(107, "An error occurred while updating to the database."),
  DATABASE_DELETE_ERROR(108, "An error occurred while deleting to the database."),
  DATABASE_LIST_ERROR(109, "An error occurred while fetching records from the database."),
  DATABASE_UPDATE_ERROR_PRODUCT(110,
      "An error occurred while updating the product in the database, please verify that the product.id is entered in the request.");

  private final Integer code;

  private final String message;
}
