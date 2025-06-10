package pe.oxxo.documents.infrastructure.config.security;

import pe.oxxo.documents.infrastructure.config.security.filter.CustomAccessDeniedHandler;
import pe.oxxo.documents.infrastructure.config.security.filter.CustomAuthenticationEntryPoint;
import pe.oxxo.documents.infrastructure.config.security.filter.JwtAuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

  private static final String ROLE_ADMIN = "ADMIN";

  private static final String ROLE_USER = "USER";

  private static final String ORDERS_ROOT_PATH = "/api/documents";

  private static final String ORDERS_PATH = "/api/documents/{id}";

  //private static final String STATUS_ROOT_PATH = "/api/orders/status";

  //private static final String STATUS_PATH = "/api/orders/status/{id}";

  private final JwtAuthorizationFilter jwtAuthorizationFilter;

  private final CustomAccessDeniedHandler customAccessDeniedHandler;

  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  public SecurityConfig(final JwtAuthorizationFilter jwtAuthorizationFilter, CustomAccessDeniedHandler customAccessDeniedHandler,
      CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
    this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    this.customAccessDeniedHandler = customAccessDeniedHandler;
    this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, ORDERS_ROOT_PATH).hasAnyRole(ROLE_ADMIN, ROLE_USER)
            .requestMatchers(HttpMethod.GET, ORDERS_PATH).hasAnyRole(ROLE_ADMIN, ROLE_USER)
            .requestMatchers(HttpMethod.POST, ORDERS_ROOT_PATH).hasAnyRole(ROLE_ADMIN, ROLE_USER)
            .requestMatchers(HttpMethod.PUT, ORDERS_PATH).hasRole(ROLE_ADMIN)
            .requestMatchers(HttpMethod.DELETE, ORDERS_PATH).hasRole(ROLE_ADMIN)
            .requestMatchers(HttpMethod.PATCH, ORDERS_PATH).hasRole(ROLE_ADMIN)
            .anyRequest().authenticated())
        .exceptionHandling(exception -> exception
            .accessDeniedHandler(customAccessDeniedHandler)
            .authenticationEntryPoint(customAuthenticationEntryPoint))
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(List.of("*"));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));
    config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  FilterRegistrationBean<CorsFilter> corsFilter() {
    FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
        new CorsFilter(corsConfigurationSource()));
    corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return corsBean;
  }
}
