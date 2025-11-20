package walletSystem.userService.user.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusRequest {
    private String status; // ACTIVE, INACTIVE, SUSPENDED
}
