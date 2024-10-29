package vn.cmcati.eid.service;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import vn.cmcati.eid.dto.request.UserCreationRequest;
import vn.cmcati.eid.dto.response.UserResponse;
import vn.cmcati.eid.entity.User;

import java.util.List;


@Service
public interface UserService {
    UserResponse createUser(UserCreationRequest request);
    @PostAuthorize("returnObject.username == authentication.name")
    UserResponse getUserById(Long id);
    @PostAuthorize("returnObject.username == authentication.name")
    UserResponse updateUser(Long id, UserCreationRequest request);
    @PreAuthorize("hasRole('ADMIN')")
    List<UserResponse> getUsers();
    UserResponse getMyInfo();
    User getCurrentUser();
}
