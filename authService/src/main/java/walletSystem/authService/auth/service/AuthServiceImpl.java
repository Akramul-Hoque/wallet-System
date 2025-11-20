package walletSystem.authService.auth.service;

import walletSystem.authService.client.UserServiceClient;
import walletSystem.authService.auth.Dto.request.LoginRequest;
import walletSystem.authService.auth.Dto.response.LoginResponse;
import walletSystem.authService.util.JwtTokenUtil;
import org.apache.http.auth.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserServiceClient userServiceClient;
    private final JwtTokenUtil jwtTokenUtil;
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(UserServiceClient userServiceClient, JwtTokenUtil jwtTokenUtil) {
        this.userServiceClient = userServiceClient;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws InvalidCredentialsException {
        try {
            // Call User Service for validation
            Map<?, ?> resp = userServiceClient.validateLogin(request);

            // If userService wrapped response in CommonResponse, handle error cases
            if (resp.containsKey("status") && "ERROR".equalsIgnoreCase(String.valueOf(resp.get("status")))) {
                Object msg = resp.get("message");
                throw new InvalidCredentialsException(msg == null ? "Invalid credentials" : msg.toString());
            }

            // Extract user data from response (either raw or wrapped under data)
            Object dataObj = resp.get("data") != null ? resp.get("data") : resp;
            Map<?, ?> dataMap = (Map<?, ?>) dataObj;

            Long userId = extractUserId(dataMap.get("userId"));
            String username = extractString(dataMap.get("username"));
            String role = extractString(dataMap.get("role"));

            // Generate JWT token
            String token = jwtTokenUtil.generateToken(username, role, userId);

            logger.info("Token generated for user: {}", username);

            return new LoginResponse(token, userId, username, role);

        } catch (Exception ex) {
            logger.error("Login failed: {}", ex.getMessage());
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    private Long extractUserId(Object idObj) throws InvalidCredentialsException {
        if (idObj == null) {
            throw new InvalidCredentialsException("User ID not found");
        }
        return idObj instanceof Number ?
                ((Number) idObj).longValue() :
                Long.parseLong(idObj.toString());
    }

    private String extractString(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}