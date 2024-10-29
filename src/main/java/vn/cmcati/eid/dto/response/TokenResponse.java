package vn.cmcati.eid.dto.response;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    UserResponse userResponse;

    String token;

    Integer active;

    Instant createdAt;

    Instant expiresAt;
}
