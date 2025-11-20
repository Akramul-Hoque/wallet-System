package walletSystem.userService.user.service;

import walletSystem.userService.exception.exception.InvalidCredentialsException;
import walletSystem.userService.user.Dto.request.KycRequest;
import walletSystem.userService.user.Dto.request.StatusRequest;
import walletSystem.userService.user.Dto.request.UserRequest;
import walletSystem.userService.user.Dto.response.AddressResponse;
import walletSystem.userService.user.Dto.response.CommonResponse;
import walletSystem.userService.user.Dto.response.UserResponse;
import walletSystem.userService.user.entity.PermanentAddress;
import walletSystem.userService.user.entity.PresentAddress;
import walletSystem.userService.user.entity.User;
import walletSystem.userService.user.event.UserCreatedEvent;
import walletSystem.userService.user.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final org.springframework.kafka.core.KafkaTemplate<String, Object> kafkaTemplate;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            ApplicationEventPublisher eventPublisher,
            org.springframework.kafka.core.KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public User validateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Wrong password");
        }
        return user;
    }

    @Override
    public CommonResponse<UserResponse> createUser(UserRequest userRequest) {
        // check unique username
        if (userRepository.findByUsername(userRequest.getUserName()).isPresent()) {
            return CommonResponse.errorResponse(400, "Username already exists");
        }

        User user = new User();
        user.setUsername(userRequest.getUserName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());
        user.setAge(userRequest.getAge());
        user.setDob(userRequest.getDob());
        user.setNid(userRequest.getNid());

        if (userRequest.getUserPermanentAddress() != null) {
            PermanentAddress pa = new PermanentAddress();
            pa.setRoad(userRequest.getUserPermanentAddress().getRoad());
            pa.setPost(userRequest.getUserPermanentAddress().getPost());
            pa.setCity(userRequest.getUserPermanentAddress().getCity());
            user.setUserPermanentAddress(pa);
        }
        if (userRequest.getUserPresentAddress() != null) {
            PresentAddress pra = new PresentAddress();
            pra.setRoad(userRequest.getUserPresentAddress().getRoad());
            pra.setPost(userRequest.getUserPresentAddress().getPost());
            pra.setCity(userRequest.getUserPresentAddress().getCity());
            user.setUserPresentAddress(pra);
        }

        user.setKycStatus("PENDING");
        user.setStatus("ACTIVE");
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        User saved = userRepository.save(user);

        // Emit UserCreated event
        try {
            UserCreatedEvent event = new UserCreatedEvent(saved.getId(), saved.getUsername());
            eventPublisher.publishEvent(event);
            kafkaTemplate.send("user.created", event);
        } catch (Exception ignored) {
        }

        UserResponse resp = mapToResponse(saved);
        return CommonResponse.successResponse(resp, "User created");
    }

    @Override
    public CommonResponse<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(u -> CommonResponse.successResponse(mapToResponse(u), "OK"))
                .orElse(CommonResponse.errorResponse(404, "User not found"));
    }

    @Override
    public CommonResponse<UserResponse> updateUser(Long id, UserRequest userRequest) {
        return userRepository.findById(id).map(u -> {
            if (userRequest.getUserName() != null)
                u.setUsername(userRequest.getUserName());
            if (userRequest.getPassword() != null)
                u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            if (userRequest.getEmail() != null)
                u.setEmail(userRequest.getEmail());
            if (userRequest.getRole() != null)
                u.setRole(userRequest.getRole());
            if (userRequest.getAge() != null)
                u.setAge(userRequest.getAge());
            if (userRequest.getDob() != null)
                u.setDob(userRequest.getDob());
            if (userRequest.getNid() != null)
                u.setNid(userRequest.getNid());

            if (userRequest.getUserPermanentAddress() != null) {
                PermanentAddress pa = u.getUserPermanentAddress() == null ? new PermanentAddress()
                        : u.getUserPermanentAddress();
                pa.setRoad(userRequest.getUserPermanentAddress().getRoad());
                pa.setPost(userRequest.getUserPermanentAddress().getPost());
                pa.setCity(userRequest.getUserPermanentAddress().getCity());
                u.setUserPermanentAddress(pa);
            }
            if (userRequest.getUserPresentAddress() != null) {
                PresentAddress pra = u.getUserPresentAddress() == null ? new PresentAddress()
                        : u.getUserPresentAddress();
                pra.setRoad(userRequest.getUserPresentAddress().getRoad());
                pra.setPost(userRequest.getUserPresentAddress().getPost());
                pra.setCity(userRequest.getUserPresentAddress().getCity());
                u.setUserPresentAddress(pra);
            }

            u.setUpdatedAt(LocalDateTime.now());
            User saved = userRepository.save(u);
            return CommonResponse.successResponse(mapToResponse(saved), "User updated");
        }).orElse(CommonResponse.errorResponse(404, "User not found"));
    }

    @Override
    public CommonResponse<String> deleteUser(Long id) {
        return userRepository.findById(id).map(u -> {
            userRepository.delete(u);
            return CommonResponse.successResponse("Deleted", "User deleted");
        }).orElse(CommonResponse.errorResponse(404, "User not found"));
    }

    @Override
    public CommonResponse<UserResponse> updateKycStatus(Long id, KycRequest kycRequest) {
        return userRepository.findById(id).map(u -> {
            u.setKycStatus(kycRequest.getKycStatus());
            u.setUpdatedAt(LocalDateTime.now());
            User saved = userRepository.save(u);
            return CommonResponse.successResponse(mapToResponse(saved), "KYC updated");
        }).orElse(CommonResponse.errorResponse(404, "User not found"));
    }

    @Override
    public CommonResponse<UserResponse> updateStatus(Long id, StatusRequest statusRequest) {
        return userRepository.findById(id).map(u -> {
            u.setStatus(statusRequest.getStatus());
            u.setUpdatedAt(LocalDateTime.now());
            User saved = userRepository.save(u);
            return CommonResponse.successResponse(mapToResponse(saved), "Status updated");
        }).orElse(CommonResponse.errorResponse(404, "User not found"));
    }

    private UserResponse mapToResponse(User u) {
        UserResponse r = new UserResponse();
        r.setUserName(u.getUsername());
        r.setPassword(null);
        r.setAge(u.getAge());
        r.setDob(u.getDob());
        r.setNid(u.getNid());
        r.setRole(u.getRole());
        if (u.getUserPermanentAddress() != null) {
            AddressResponse ar = new AddressResponse();
            ar.setRoad(u.getUserPermanentAddress().getRoad());
            ar.setPost(u.getUserPermanentAddress().getPost());
            ar.setCity(u.getUserPermanentAddress().getCity());
            r.setUserPermanentAddress(ar);
        }
        if (u.getUserPresentAddress() != null) {
            AddressResponse ar = new AddressResponse();
            ar.setRoad(u.getUserPresentAddress().getRoad());
            ar.setPost(u.getUserPresentAddress().getPost());
            ar.setCity(u.getUserPresentAddress().getCity());
            r.setUserPresentAddress(ar);
        }
        return r;
    }
}
