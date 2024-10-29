package vn.cmcati.eid.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.cmcati.eid.entity.PaymentPlan;
import vn.cmcati.eid.entity.UserPayment;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenCreationRequest {
    UserPayment userPayment;
}
