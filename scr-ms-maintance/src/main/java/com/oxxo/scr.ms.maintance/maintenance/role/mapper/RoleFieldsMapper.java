package com.oxxo.scr.ms.maintance.maintenance.role.mapper;

import com.oxxo.scr.ms.maintance.event.entities.Role;
import com.oxxo.scr.ms.maintance.maintenance.role.dto.request.RoleRequestDto;
import com.oxxo.scr.ms.maintance.maintenance.role.dto.response.RoleResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleFieldsMapper {

  Role sourceToDestination(final RoleRequestDto source);

  RoleResponseDto destinationToSource(final Role destination);

  List<RoleResponseDto> toDto(final List<Role> entityList);
}
