package com.oxxo.scr.ms.maintance.maintenance.user.api.dto.request;

import com.oxxo.scr.ms.maintance.maintenance.role.dto.request.RoleRequestDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor

public class UserRequestDto {

  private Integer id;

  @NotNull(message = "The username should not be null")
  @Size(min = 2, max = 20, message = "The size must be between 2 and 20 characters")
  private String username;

  @NotNull(message = "The password should not be null")
  @Size(min = 2, max = 50, message = "The size must be between 2 and 50 characters")
  private String password;

  private Boolean isActive;

  private List<RoleRequestDto> roles;
}
