package com.oxxo.scr.ms.maintance.maintenance.role.service;

import com.oxxo.scr.ms.maintance.maintenance.role.dto.request.RoleRequestDto;
import com.oxxo.scr.ms.maintance.maintenance.role.dto.response.RoleResponseDto;
import com.oxxo.scr.ms.common.utils.GenericResponse;

import java.util.List;

public interface RoleService {

  /**
   * Method used to list all roles.
   *
   * @return List of RoleResponseDto.
   */
  GenericResponse<List<RoleResponseDto>> getAllRoles();

  /**
   * Method used to save the role.
   *
   * @param roleRequestDto The role request dto.
   * @return List of RoleResponseDto.
   */
  GenericResponse<RoleResponseDto> save(final RoleRequestDto roleRequestDto);

  /**
   * Method used to delete the role.
   *
   * @param id The id of the role.
   * @return List of RoleResponseDto.
   */
  GenericResponse<RoleResponseDto> deleteById(final Integer id);

  /**
   * Method used to update the status of the role.
   *
   * @param isActive True if is active or false en otherwise.
   * @return List of RoleResponseDto.
   */
  GenericResponse<RoleResponseDto> updateBrandStatus(final Boolean isActive, final Integer id);

  /**
   * Method used to find the role by id.
   *
   * @param id The id of the role.
   * @return List of RoleResponseDto.
   */
  GenericResponse<RoleResponseDto> findById(final Integer id);
}
