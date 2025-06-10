package com.oxxo.scr.ms.maintance.maintenance.role.controller;

import com.oxxo.scr.ms.common.exception.GenericUnprocessableEntityException;
import com.oxxo.scr.ms.maintance.maintenance.role.dto.request.RoleRequestDto;
import com.oxxo.scr.ms.maintance.maintenance.role.dto.response.RoleResponseDto;
import com.oxxo.scr.ms.maintance.maintenance.role.service.RoleService;
import com.oxxo.scr.ms.maintance.maintenance.role.utils.RoleConstants;
import com.oxxo.scr.ms.common.utils.GenericResponse;
import com.oxxo.scr.ms.maintance.utils.MaintenanceUtils;
import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/roles")
public class RoleController {

  private final RoleService service;

  public RoleController(final RoleService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<GenericResponse<List<RoleResponseDto>>> getAllRoles() {
    return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllRoles());
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<RoleResponseDto>> getRoleById(@PathVariable(value = "id") final Integer id) {
    final GenericResponse<RoleResponseDto> response = this.service.findById(id);
    if (Objects.nonNull(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PostMapping
  public ResponseEntity<GenericResponse<RoleResponseDto>> saveRole(@RequestBody final RoleRequestDto roleRequestDto) {
    if (ObjectUtils.isNotEmpty(roleRequestDto.getId())) {
      throw new GenericUnprocessableEntityException(RoleConstants.UNPROCESSABLE_ENTITY_EXCEPTION);
    } else {
      MaintenanceUtils.validRequestDto(roleRequestDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(roleRequestDto));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<RoleResponseDto>> updateBrand(@Valid @PathVariable(value = "id") final Integer id,
      @RequestBody final RoleRequestDto roleRequestDto) {
    final GenericResponse<RoleResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      roleRequestDto.setId(id);
      MaintenanceUtils.validRequestDto(roleRequestDto);
      return ResponseEntity.status(HttpStatus.OK).body(this.service.save(roleRequestDto));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<GenericResponse<RoleResponseDto>> updateRoleStatus(@PathVariable(value = "id") final Integer id,
      @RequestParam(value = "isActive") final Boolean isActive) {
    final GenericResponse<RoleResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(this.service.updateBrandStatus(isActive, id));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<RoleResponseDto>> deleteRole(@PathVariable(value = "id") final Integer id) {
    final GenericResponse<RoleResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(this.service.deleteById(id));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }
}
