package walletSystem.userService.user.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserValidationResponse {

    private boolean valid;
    private Long userId;
    private String username;
    private String role;
    private String error;

    public UserValidationResponse(Long userId, String username, String role) {
        this.valid = true;
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.error = null;
    }

    public static UserValidationResponse failure(String error) {
        UserValidationResponse res = new UserValidationResponse();
        res.valid = false;
        res.error = error;
        return res;
    }
}

