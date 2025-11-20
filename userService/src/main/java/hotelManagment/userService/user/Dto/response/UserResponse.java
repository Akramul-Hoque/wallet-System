package hotelManagment.userService.user.Dto.response;

import hotelManagment.userService.user.Dto.request.UserPermanentAddress;
import hotelManagment.userService.user.Dto.request.UserPresentAddress;
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
