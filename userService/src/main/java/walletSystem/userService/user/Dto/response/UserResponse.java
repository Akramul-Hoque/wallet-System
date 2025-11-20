package walletSystem.userService.user.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userName;
    public String password;
    private String age;
    private String dob;
    private String nid;
    private String role;
    private AddressResponse userPermanentAddress;
    private AddressResponse userPresentAddress;

}
