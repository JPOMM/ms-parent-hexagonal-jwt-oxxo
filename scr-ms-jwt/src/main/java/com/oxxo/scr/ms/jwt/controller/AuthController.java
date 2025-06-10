package com.oxxo.scr.ms.jwt.controller;

import com.oxxo.scr.ms.common.utils.GenericResponse;
import com.oxxo.scr.ms.common.utils.GenericUtils;
import com.oxxo.scr.ms.jwt.api.dto.request.LoginRequest;
import com.oxxo.scr.ms.jwt.api.dto.response.TokenResponse;
import com.oxxo.scr.ms.common.event.models.RoleResponseDto;
import com.oxxo.scr.ms.common.event.models.UserResponseDto;
import com.oxxo.scr.ms.jwt.exceptions.JwtException;
import com.oxxo.scr.ms.jwt.services.UserService;
import com.oxxo.scr.ms.jwt.utils.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/auth")
@Component
@Slf4j
public class AuthController {

  private final UserService userService;

  @Getter
  private SecretKey secretKey;

  @Value("${jwt.secret}")
  public void setSecret(final String secret) {
    secretKey = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public AuthController(final UserService userService) {
    this.userService = userService;

  }

  @Bean
  private static BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @PostMapping("/login")
  public ResponseEntity<GenericResponse<TokenResponse>> login(@RequestBody final LoginRequest loginRequest,
                                                              HttpServletResponse httpServletResponse) {
    if (ObjectUtils.isEmpty(loginRequest.username()) || ObjectUtils.isEmpty(loginRequest.password())) {
      throw new JwtException(JwtErrorMessage.MAL_FORMATTED);
    }
    final UserResponseDto user = userService.validateUser(loginRequest.username());
    if (ObjectUtils.isEmpty(user) || Boolean.TRUE.equals(!user.getIsActive()) || !passwordEncoder().matches(loginRequest.password(),
        user.getPassword())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(GenericUtils.buildGenericResponseWarning(JwtConstants.INVALID_USER, null));
    }
    final List<String> roles = user.getRoles().stream()
        .map(RoleResponseDto::getDescription)
        .toList();
    final Claims claims = Jwts.claims()
        .add("username", user.getUsername())
        .add("roles", roles)
        .build();
    final String token = Jwts.builder()
        .subject(user.getUsername())
        .claims(claims)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
        .signWith(getSecretKey())
        .compact();
    httpServletResponse.addHeader(TokenJwtConfig.HEADER_AUTHORIZATION, token);
    return ResponseEntity.ok(
        GenericUtils.buildGenericResponseSuccess(JwtConstants.GENERATED_TOKEN, new TokenResponse(token, user.getUsername())));
  }

  @PostMapping("/validate")
  public ResponseEntity<GenericResponse<Object>> validateToken(
      @RequestHeader(TokenJwtConfig.HEADER_AUTHORIZATION) final String authHeader) {
    try {
      if (authHeader == null || !authHeader.startsWith(TokenJwtConfig.PREFIX_TOKEN)) {
        throw new JwtException(JwtErrorMessage.INVALID_TOKEN);
      }
      final String token = StringUtils.substring(authHeader, TokenJwtConfig.PREFIX_TOKEN.length());
      Claims claims = Jwts.parser()
          .verifyWith(getSecretKey())
          .build()
          .parseSignedClaims(token)
          .getPayload();
      return ResponseEntity.ok(GenericUtils.buildGenericResponseSuccess(JwtConstants.VALID_TOKEN, claims));
    } catch (final Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(GenericUtils.buildGenericResponseError(JwtConstants.INVALID_TOKEN, null));
    }
  }
}
