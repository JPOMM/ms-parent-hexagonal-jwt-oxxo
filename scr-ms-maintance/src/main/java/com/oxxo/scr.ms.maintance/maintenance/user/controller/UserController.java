package com.oxxo.scr.ms.maintance.maintenance.user.controller;

import com.oxxo.scr.ms.common.exception.GenericUnprocessableEntityException;
import com.oxxo.scr.ms.maintance.maintenance.user.api.dto.request.UserRequestDto;
import com.oxxo.scr.ms.maintance.maintenance.user.api.dto.response.UserResponseDto;
import com.oxxo.scr.ms.maintance.maintenance.user.service.UserService;
import com.oxxo.scr.ms.maintance.maintenance.user.utils.UserConstants;
import com.oxxo.scr.ms.common.utils.GenericResponse;
import com.oxxo.scr.ms.common.utils.GenericResponseConstants;
import com.oxxo.scr.ms.common.utils.GenericUtils;
import com.oxxo.scr.ms.maintance.utils.MaintenanceUtils;
import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/users")
public class UserController {

  private final UserService service;

  private final BCryptPasswordEncoder passwordEncoder;

  public UserController(final UserService service, final BCryptPasswordEncoder passwordEncoder) {
    this.service = service;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping
  public ResponseEntity<GenericResponse<List<UserResponseDto>>> retrieveAllUsers() {
    return ResponseEntity.status(HttpStatus.OK).body(this.service.retrieveAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<UserResponseDto>> retrieveUserById(@PathVariable(value = "id") final Integer id) {
    final GenericResponse<UserResponseDto> response = this.service.findById(id);
    if (Objects.nonNull(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PostMapping
  public ResponseEntity<GenericResponse<UserResponseDto>> createUser(@RequestBody final UserRequestDto userRequestDto) {
    if (ObjectUtils.isNotEmpty(userRequestDto.getId())) {
      throw new GenericUnprocessableEntityException(UserConstants.UNPROCESSABLE_ENTITY_EXCEPTION);
    } else {
      MaintenanceUtils.validRequestDto(userRequestDto);
      userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
      return ResponseEntity.status(HttpStatus.CREATED).body(this.service.create(userRequestDto));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<UserResponseDto>> updateUser(@Valid @PathVariable(value = "id") final Integer id,
      @RequestBody final UserRequestDto userRequestDto) {
    final GenericResponse<UserResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      userRequestDto.setId(id);
      MaintenanceUtils.validRequestDto(userRequestDto);
      userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
      return ResponseEntity.status(HttpStatus.OK).body(this.service.create(userRequestDto));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<GenericResponse<UserResponseDto>> updateUserStatus(@PathVariable(value = "id") final Integer id,
      @RequestParam(value = "isActive") final Boolean isActive) {
    final GenericResponse<UserResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(this.service.updateUserStatus(isActive, id));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<UserResponseDto>> deleteUser(@PathVariable(value = "id") final Integer id) {
    final GenericResponse<UserResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(this.service.deleteById(id));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @GetMapping("/authorized")
  public ResponseEntity<GenericResponse<Map<String, Object>>> retrieveAuthorizedUsers(@RequestParam(name = "code") final String code) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericUtils.buildGenericResponseSuccess(GenericResponseConstants.RETRIEVE_CODE_MSG, Collections.singletonMap("code", code)));
  }

  @GetMapping("/login")
  public ResponseEntity<GenericResponse<UserResponseDto>> loginByUsername(@RequestParam(name = "username") final String username) {
    final GenericResponse<UserResponseDto> response = this.service.findByUsername(username);
    if (Objects.nonNull(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }
}
