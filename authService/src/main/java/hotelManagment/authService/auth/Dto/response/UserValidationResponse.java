package hotelManagment.authService.auth.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserValidationResponse {
    private Long id;
    private String username;
    private String role;
}
