package vn.cmcati.eid.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import vn.cmcati.eid.entity.UserPayment;

import java.time.Instant;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class TokenApiKeyResponse {
    String token;
    Integer active;

    Instant expiresAt;
    Integer remainingRequests;
    Long userPaymentId;
    public TokenApiKeyResponse(String token, Integer active, Instant expiresAt, Integer remainingRequests, Long userPaymentId) {
        this.token = token;
        this.active = active;
        this.expiresAt = expiresAt;
        this.remainingRequests = remainingRequests;
        this.userPaymentId = userPaymentId;
    }
}
