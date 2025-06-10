package com.oxxo.scr.ms.maintance.maintenance.user.mapper;

import com.oxxo.scr.ms.maintance.event.entities.User;
import com.oxxo.scr.ms.maintance.maintenance.user.api.dto.request.UserRequestDto;
import com.oxxo.scr.ms.maintance.maintenance.user.api.dto.response.UserResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserFieldsMapper {

  User sourceToDestination(final UserRequestDto source);

  UserResponseDto destinationToSource(final User destination);

  List<UserResponseDto> toDto(final List<User> entityList);
}
