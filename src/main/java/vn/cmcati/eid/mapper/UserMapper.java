package vn.cmcati.eid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.cmcati.eid.dto.request.UserCreationRequest;
import vn.cmcati.eid.dto.response.UserResponse;
import vn.cmcati.eid.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserCreationRequest userCreationRequest);
}
