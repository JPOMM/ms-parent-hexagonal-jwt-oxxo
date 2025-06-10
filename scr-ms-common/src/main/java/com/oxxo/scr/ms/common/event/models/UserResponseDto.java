package com.oxxo.scr.ms.common.event.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

  private Integer id;

  private String username;

  private String password;

  private Boolean isActive;

  private List<RoleResponseDto> roles;
}
