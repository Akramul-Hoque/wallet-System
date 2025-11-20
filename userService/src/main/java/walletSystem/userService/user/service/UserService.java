package walletSystem.userService.user.service;

import walletSystem.userService.user.Dto.request.UserRequest;
import walletSystem.userService.user.Dto.request.KycRequest;
import walletSystem.userService.user.Dto.request.StatusRequest;
import walletSystem.userService.user.Dto.response.CommonResponse;
import walletSystem.userService.user.Dto.response.UserResponse;
import walletSystem.userService.user.entity.User;

public interface UserService {
    // CRUD + KYC/status operations
    CommonResponse<UserResponse> createUser(
            UserRequest userRequest);

    CommonResponse<UserResponse> getUserById(
            Long id);

    CommonResponse<UserResponse> updateUser(
            Long id,
            UserRequest userRequest);

    CommonResponse<String> deleteUser(Long id);

    CommonResponse<UserResponse> updateKycStatus(
            Long id,
            KycRequest kycRequest);

    CommonResponse<UserResponse> updateStatus(
            Long id,
            StatusRequest statusRequest);

    User validateUser(String userName, String password);
}
