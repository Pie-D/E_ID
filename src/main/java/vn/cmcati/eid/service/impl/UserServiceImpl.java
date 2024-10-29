package vn.cmcati.eid.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.var;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import vn.cmcati.eid.dto.request.UserCreationRequest;
import vn.cmcati.eid.dto.response.UserResponse;
import vn.cmcati.eid.entity.User;
import vn.cmcati.eid.enums.Role;
import vn.cmcati.eid.exception.AppException;
import vn.cmcati.eid.exception.ErrorCode;
import vn.cmcati.eid.mapper.UserMapper;
import vn.cmcati.eid.repository.UserRepository;
import vn.cmcati.eid.service.UserService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setCreatedAt(Instant.now());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Role.USER.name());
        User userSaved = userRepository.save(user);
        return userMapper.toUserResponse(userSaved);
    }

    @Override
    public UserResponse getUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.NOT_FOUND)
        );
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserCreationRequest request) {
        var user = userRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.NOT_FOUND)
        );
        userMapper.updateUser(user, request);
        userRepository.save(user);
        return userMapper.toUserResponse(user);

    }

    @Override
    public List<UserResponse> getUsers() {
        var users = userRepository.findAll();
        return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }
    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication().getPrincipal());
        String name = context.getAuthentication().getName();
        System.out.println("---------------" + name);
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();

            String userId = jwt.getClaimAsString("id");

            if (userId != null) {
                return userRepository.findById(Long.valueOf(userId)).orElse(null);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
