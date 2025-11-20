package hotelManagment.authService.auth.controller;

import hotelManagment.authService.auth.Dto.request.LoginRequest;
import hotelManagment.authService.auth.Dto.response.LoginResponse;
import hotelManagment.authService.auth.service.AuthService;
import hotelManagment.authService.common.response.base.CommonResponse;
import hotelManagment.authService.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            CommonResponse resp = CommonResponse.success(response, "Authenticated", 0, HttpStatus.OK.value());
            return ResponseEntity.ok(resp);
        } catch (InvalidCredentialsException e) {
            CommonResponse resp = CommonResponse.failure("Invalid credentials", 1003, HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
        } catch (Exception e) {
            CommonResponse resp = CommonResponse.failure("Authentication failed", 1004, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }
}
