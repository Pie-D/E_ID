package vn.cmcati.eid.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.cmcati.eid.entity.PaymentMethod;
import vn.cmcati.eid.entity.UserPayment;

import java.math.BigDecimal;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentHistoryRequest {
    String orderId;
    String requestId;
    BigDecimal totalAmount;
    PaymentMethod paymentMethod;
    UserPayment userPayment;

}
