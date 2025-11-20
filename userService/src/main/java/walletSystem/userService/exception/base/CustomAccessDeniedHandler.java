package walletSystem.userService.exception.base;


import com.fasterxml.jackson.databind.ObjectMapper;
import walletSystem.userService.common.response.base.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        if (!response.isCommitted()) {
            ErrorResponse customErrorData = new ErrorResponse(HttpStatus.FORBIDDEN, 99,"Un Authorised");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            OutputStream responseStream = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(responseStream, customErrorData);
            responseStream.flush();
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied");
        }
    }
}