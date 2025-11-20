package hotelManagment.userService.user.controller;

import hotelManagment.userService.exception.exception.InvalidCredentialsException;
import hotelManagment.userService.user.Dto.request.LoginRequest;
import hotelManagment.userService.user.Dto.response.UserValidationResponse;
import hotelManagment.userService.user.entity.User;
import hotelManagment.userService.user.service.UserService;
import hotelManagment.userService.common.response.base.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/users/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("validate-login")
    public ResponseEntity<CommonResponse> validateLogin(@RequestBody LoginRequest request) {
        try {
            User user = userService.validateUser(request.getUserName(), request.getPassword());

            UserValidationResponse data = new UserValidationResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getRole()
            );

            CommonResponse resp = CommonResponse.success(data, "Validated", 0, HttpStatus.OK.value());
            return ResponseEntity.ok(resp);
        } catch ( InvalidCredentialsException e) {
            CommonResponse resp = CommonResponse.failure("INVALID_CREDENTIALS", 1002, HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
        }
    }
}
