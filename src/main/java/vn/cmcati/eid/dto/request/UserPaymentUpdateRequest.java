package vn.cmcati.eid.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserPaymentUpdateRequest {
    Long userPaymentId;
    Long paymentPlanId;
    Long paymentMethodId;
    String orderId;
    String requestId;
}