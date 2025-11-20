package walletSystem.authService.service;

import walletSystem.authService.auth.Dto.request.LoginRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class UserValidationClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String userServiceValidateUrl;

    public UserValidationClient(@Value("${userservice.url}") String userServiceBase) {
        this.userServiceValidateUrl = userServiceBase + "/api/internal/users/validate-login";
    }

    public Map<?, ?> validateLogin(LoginRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> resp = restTemplate.postForEntity(userServiceValidateUrl, entity, Map.class);
        return resp.getBody();
    }
}
