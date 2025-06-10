package pe.oxxo.documents.infrastructure.config.security.filter;

import pe.oxxo.documents.infrastructure.utils.OrderErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final String CONTENT_TYPE = "application/json";

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(CONTENT_TYPE);

    Map<String, Object> body = new HashMap<>();
    body.put("message", OrderErrorMessage.INVALID_TOKEN.getErrorMessage());
    body.put("error", authException.getMessage());
    body.put("path", request.getRequestURI());

    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
  }
}
