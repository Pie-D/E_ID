package vn.cmcati.eid.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPaymentFromPaymentHistoryResponse {
    UserResponse user;

    PaymentMethodResponse scheduledPaymentMethod;

    PaymentPlanResponse paymentPlan;

    Instant paymentDate;

    Integer remainingRequests;

    TokenResponse token;

}
