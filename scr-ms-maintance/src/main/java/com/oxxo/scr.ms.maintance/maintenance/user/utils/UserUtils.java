package com.oxxo.scr.ms.maintance.maintenance.user.utils;

import com.oxxo.scr.ms.maintance.maintenance.user.api.dto.response.UserResponseDto;
import com.oxxo.scr.ms.common.utils.GenericResponse;
import com.oxxo.scr.ms.common.utils.GenericResponseConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {

  /**
   * Method use to build generic response error.
   *
   * @return GenericResponse of BrandResponseDto The generic response.
   */
  public static GenericResponse<UserResponseDto> buildGenericResponseError() {
    return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR,
        StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.INCORRECT_OPERATION, UserConstants.NO_EXIST),
        null);
  }

  /**
   * Method use to build generic response success.
   *
   * @return GenericResponse of BrandResponseDto The generic response.
   */
  public static GenericResponse<UserResponseDto> buildGenericResponseSuccess(final UserResponseDto userResponseDto) {
    return new GenericResponse<>(GenericResponseConstants.RPTA_OK,
        StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.CORRECT_OPERATION), userResponseDto);
  }
}
