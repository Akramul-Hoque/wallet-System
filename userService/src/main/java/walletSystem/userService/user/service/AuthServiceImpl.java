//package hotelManagment.userService.user.service;
//
//import hotelManagment.userService.user.Dto.request.LoginRequest;
//import hotelManagment.userService.user.Dto.response.LoginResponse;
//import hotelManagment.userService.util.JwtTokenUtil;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthServiceImpl implements AuthService {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenUtil jwtTokenUtil;
//    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
//        this.authenticationManager = authenticationManager;
//        this.jwtTokenUtil = jwtTokenUtil;
//    }
//
//    public LoginResponse authenticateUser(LoginRequest loginRequest) {
//        // Authenticate the user
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
//        );
//
//        // Generate JWT token
//        String jwtToken = jwtTokenUtil.generateToken(authentication);
//
//        // Return the JWT token in response
//        return new LoginResponse(jwtToken);
//    }
//}
