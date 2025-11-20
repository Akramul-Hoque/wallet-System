package hotelManagment.userService.user.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String userName;
    public String password;
    private String age;
//    private String address;
    private String email;
    private String dob;
    private String nid;
    private String role;
    private UserPermanentAddress userPermanentAddress;
    private UserPresentAddress userPresentAddress;

}
