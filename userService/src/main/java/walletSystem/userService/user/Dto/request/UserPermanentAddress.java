package walletSystem.userService.user.Dto.request;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPermanentAddress {
    private String road;
    private String post;
    private String city;
}
