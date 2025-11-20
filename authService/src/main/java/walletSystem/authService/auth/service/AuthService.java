package walletSystem.authService.auth.service;

import walletSystem.authService.auth.Dto.request.LoginRequest;
import walletSystem.authService.auth.Dto.response.LoginResponse;
import org.apache.http.auth.InvalidCredentialsException;


public interface AuthService {
    LoginResponse login(LoginRequest request) throws InvalidCredentialsException;
}
