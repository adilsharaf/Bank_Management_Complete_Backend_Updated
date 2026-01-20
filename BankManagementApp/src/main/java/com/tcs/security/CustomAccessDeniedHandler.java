package com.tcs.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) 
            throws IOException, ServletException {

        // Set response status to FORBIDDEN (HTTP 403)
        response.setStatus(HttpStatus.FORBIDDEN.value());
 
        // Set response content type to JSON
        response.setContentType("application/json");

        // Example custom message
        String message = "{\"message\":\"Access Denied: You do not have permission to access this resource.\"}";

        // Write the message to the response body
        response.getWriter().write(message);
    }
}
