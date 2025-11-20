package walletSystem.userService.user.controller;

import walletSystem.userService.exception.exception.InvalidCredentialsException;
import walletSystem.userService.user.Dto.request.KycRequest;
import walletSystem.userService.user.Dto.request.LoginRequest;
import walletSystem.userService.user.Dto.request.StatusRequest;
import walletSystem.userService.user.Dto.request.UserRequest;
import walletSystem.userService.user.Dto.response.UserValidationResponse;
import walletSystem.userService.user.entity.User;
import walletSystem.userService.user.service.UserService;
import walletSystem.userService.common.response.base.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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
                    user.getRole());

            CommonResponse resp = CommonResponse.success(data, "Validated", 0, HttpStatus.OK.value());
            return ResponseEntity.ok(resp);
        } catch (InvalidCredentialsException e) {
            CommonResponse resp = CommonResponse.failure("INVALID_CREDENTIALS", 1002, HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
        }
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createUser(@RequestBody UserRequest request) {
        var svc = userService.createUser(request);
        if (svc.getCode() == 200) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(CommonResponse.success(svc.getResponse(), svc.getMessage(), 0, HttpStatus.CREATED.value()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.failure(svc.getMessage(), 1001, HttpStatus.BAD_REQUEST.value()));
    }

    @GetMapping("{id}")
    public ResponseEntity<CommonResponse> getUserById(@org.springframework.web.bind.annotation.PathVariable Long id) {
        var svc = userService.getUserById(id);
        if (svc.getCode() == 200) {
            return ResponseEntity
                    .ok(CommonResponse.success(svc.getResponse(), svc.getMessage(), 0, HttpStatus.OK.value()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CommonResponse.failure(svc.getMessage(), 1004, HttpStatus.NOT_FOUND.value()));
    }

    @PutMapping("{id}")
    public ResponseEntity<CommonResponse> updateUser(@org.springframework.web.bind.annotation.PathVariable Long id,
            @RequestBody UserRequest request) {
        var svc = userService.updateUser(id, request);
        if (svc.getCode() == 200) {
            return ResponseEntity
                    .ok(CommonResponse.success(svc.getResponse(), svc.getMessage(), 0, HttpStatus.OK.value()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CommonResponse.failure(svc.getMessage(), 1005, HttpStatus.NOT_FOUND.value()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommonResponse> deleteUser(@org.springframework.web.bind.annotation.PathVariable Long id) {
        var svc = userService.deleteUser(id);
        if (svc.getCode() == 200) {
            return ResponseEntity
                    .ok(CommonResponse.success(svc.getResponse(), svc.getMessage(), 0, HttpStatus.OK.value()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CommonResponse.failure(svc.getMessage(), 1006, HttpStatus.NOT_FOUND.value()));
    }

    @PostMapping("{id}/kyc")
    public ResponseEntity<CommonResponse> updateKyc(@org.springframework.web.bind.annotation.PathVariable Long id,
            @RequestBody KycRequest req) {
        var svc = userService.updateKycStatus(id, req);
        if (svc.getCode() == 200) {
            return ResponseEntity
                    .ok(CommonResponse.success(svc.getResponse(), svc.getMessage(), 0, HttpStatus.OK.value()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CommonResponse.failure(svc.getMessage(), 1007, HttpStatus.NOT_FOUND.value()));
    }

    @PostMapping("{id}/status")
    public ResponseEntity<CommonResponse> updateStatus(@org.springframework.web.bind.annotation.PathVariable Long id,
            @RequestBody StatusRequest req) {
        var svc = userService.updateStatus(id, req);
        if (svc.getCode() == 200) {
            return ResponseEntity
                    .ok(CommonResponse.success(svc.getResponse(), svc.getMessage(), 0, HttpStatus.OK.value()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CommonResponse.failure(svc.getMessage(), 1008, HttpStatus.NOT_FOUND.value()));
    }
}
