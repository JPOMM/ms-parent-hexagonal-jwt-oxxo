package com.oxxo.scr.ms.maintance.maintenance.user.service;

import com.oxxo.scr.ms.maintance.event.entities.Role;
import com.oxxo.scr.ms.maintance.event.entities.User;
import com.oxxo.scr.ms.maintance.maintenance.role.dto.request.RoleRequestDto;
import com.oxxo.scr.ms.maintance.maintenance.role.mapper.RoleFieldsMapper;
import com.oxxo.scr.ms.maintance.maintenance.role.repository.RoleRepository;
import com.oxxo.scr.ms.maintance.maintenance.role.utils.RoleConstants;
import com.oxxo.scr.ms.maintance.maintenance.user.api.dto.request.UserRequestDto;
import com.oxxo.scr.ms.maintance.maintenance.user.api.dto.response.UserResponseDto;
import com.oxxo.scr.ms.maintance.maintenance.user.mapper.UserFieldsMapper;
import com.oxxo.scr.ms.maintance.maintenance.user.repository.UserRepository;
import com.oxxo.scr.ms.maintance.maintenance.user.utils.UserConstants;
import com.oxxo.scr.ms.maintance.maintenance.user.utils.UserUtils;
import com.oxxo.scr.ms.common.utils.GenericResponse;
import com.oxxo.scr.ms.common.utils.GenericResponseConstants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserFieldsMapper mapper;

  private final UserRepository repository;

  private final RoleRepository roleRepository;

  private final RoleFieldsMapper roleFieldsMapper;

  @Override
  public GenericResponse<List<UserResponseDto>> retrieveAllUsers() {
    final List<User> brands = this.repository.findAll();
    return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION, this.mapper.toDto(brands));
  }

  @Override
  public GenericResponse<UserResponseDto> create(final UserRequestDto userRequestDto) {
    final User user = this.mapper.sourceToDestination(userRequestDto);
    final List<Role> persistedRoles = new ArrayList<>();
    if (ObjectUtils.isNotEmpty(user.getRoles())) {
      for (final RoleRequestDto roleRequestDto : userRequestDto.getRoles()) {
        final Role rol;
        if (Objects.nonNull(roleRequestDto.getId())) {
          rol = this.roleRepository.findById(roleRequestDto.getId()).orElseThrow(() -> new RuntimeException(RoleConstants.NO_EXIST));
        } else {
          rol = new Role();
          rol.setDescription(roleRequestDto.getDescription());
          rol.setIsActive(roleRequestDto.getIsActive());
        }
        persistedRoles.add(this.roleRepository.save(rol));
      }
      user.setRoles(persistedRoles);
    }
    return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION,
        this.mapper.destinationToSource(this.repository.save(user)));
  }

  @Override
  public GenericResponse<UserResponseDto> deleteById(final Integer id) {
    final Optional<User> user = this.repository.findById(id);
    if (user.isPresent()) {
      this.repository.deleteById(id);
      return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION, null);
    } else {
      return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR,
          StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.INCORRECT_OPERATION, UserConstants.NO_EXIST),
          null);
    }
  }

  @Override
  @Transactional
  public GenericResponse<UserResponseDto> updateUserStatus(final Boolean isActive, final Integer id) {
    final Optional<User> userOptional = this.repository.findById(id);
    if (userOptional.isPresent()) {
      this.repository.disabledOrEnabledUser(isActive, id);
      return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION, null);
    } else {
      return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR,
          StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.INCORRECT_OPERATION, UserConstants.NO_EXIST),
          null);
    }
  }

  @Override
  public GenericResponse<UserResponseDto> findById(final Integer id) {
    final Optional<User> user = this.repository.findById(id);
    return user.map(value -> UserUtils.buildGenericResponseSuccess(this.mapper.destinationToSource(value)))
        .orElseGet(UserUtils::buildGenericResponseError);
  }

  @Override
  public GenericResponse<UserResponseDto> findByUsername(final String userName) {
    final Optional<User> user = this.repository.findByUsername(userName);
    return user.map(value -> UserUtils.buildGenericResponseSuccess(this.mapper.destinationToSource(value)))
        .orElseGet(UserUtils::buildGenericResponseError);
  }
}
