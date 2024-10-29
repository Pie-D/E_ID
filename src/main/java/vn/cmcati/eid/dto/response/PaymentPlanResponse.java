package vn.cmcati.eid.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentPlanResponse {

    String planName;
    BigDecimal price;
    Integer maxRequests;
    Set<ApiTypeResponse>acceptedApiTypes;
    String rolePlan;
    Boolean isAdditionalPlan;
}