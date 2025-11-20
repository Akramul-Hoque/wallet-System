package hotelManagment.userService.user.service;
import hotelManagment.userService.user.Dto.request.UserRequest;
import hotelManagment.userService.user.Dto.response.CommonResponse;
import hotelManagment.userService.user.Dto.response.UserResponse;
import hotelManagment.userService.user.entity.User;

import java.util.Optional;

public interface UserService {
//    CommonResponse createUser(UserRequest user);
//
//    CommonResponse<UserResponse> getUserByUsername(String username);

    User validateUser(String userName, String password);
}
