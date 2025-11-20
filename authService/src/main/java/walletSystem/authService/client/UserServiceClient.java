package walletSystem.authService.client;

import walletSystem.authService.auth.Dto.request.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "user-service", url = "${user-service.url:http://localhost:8081}")
public interface UserServiceClient {

    @PostMapping("/api/internal/users/validate-login")
    Map<?, ?> validateLogin(@RequestBody LoginRequest request);
}

