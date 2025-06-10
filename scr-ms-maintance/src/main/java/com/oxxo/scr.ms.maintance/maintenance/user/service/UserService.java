package com.oxxo.scr.ms.maintance.maintenance.user.service;

import com.oxxo.scr.ms.maintance.maintenance.user.api.dto.request.UserRequestDto;
import com.oxxo.scr.ms.maintance.maintenance.user.api.dto.response.UserResponseDto;
import com.oxxo.scr.ms.common.utils.GenericResponse;

import java.util.List;

public interface UserService {

  /**
   * Method used to list all users.
   *
   * @return List of UserResponseDto.
   */
  GenericResponse<List<UserResponseDto>> retrieveAllUsers();

  /**
   * Method used to save the users.
   *
   * @param userRequestDto The user request dto.
   * @return List of UserResponseDto.
   */
  GenericResponse<UserResponseDto> create(final UserRequestDto userRequestDto);

  /**
   * Method used to delete the user.
   *
   * @param id The id of the user.
   * @return List of UserResponseDto.
   */
  GenericResponse<UserResponseDto> deleteById(final Integer id);

  /**
   * Method used to update the status of the user.
   *
   * @param isActive True if is active or false en otherwise.
   * @return List of UserResponseDto.
   */
  GenericResponse<UserResponseDto> updateUserStatus(final Boolean isActive, final Integer id);

  /**
   * Method used to find the user by id.
   *
   * @param id The id of the user.
   * @return List of UserResponseDto.
   */
  GenericResponse<UserResponseDto> findById(final Integer id);

  /**
   * Method used to find the user by username.
   *
   * @param userName The id of the user.
   * @return List of UserResponseDto.
   */
  GenericResponse<UserResponseDto> findByUsername(final String userName);
}
