package hotelManagment.authService.auth.service;

import hotelManagment.authService.auth.Dto.request.LoginRequest;
import hotelManagment.authService.auth.Dto.response.LoginResponse;
import org.apache.http.auth.InvalidCredentialsException;


public interface AuthService {
    LoginResponse login(LoginRequest request) throws InvalidCredentialsException;
}
