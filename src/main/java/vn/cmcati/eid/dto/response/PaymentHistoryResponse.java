package vn.cmcati.eid.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentHistoryResponse {
    String orderId;
    String requestId;
    Instant createTime;
    BigDecimal totalAmount;
    PaymentMethodResponse paymentMethod;
    UserPaymentFromPaymentHistoryResponse userPayment;
}
