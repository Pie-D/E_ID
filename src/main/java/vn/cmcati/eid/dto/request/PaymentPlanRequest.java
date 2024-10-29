package vn.cmcati.eid.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.cmcati.eid.entity.ApiType;
import vn.cmcati.eid.entity.UserPayment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentPlanRequest {

    String planName;
    BigDecimal price;
    Integer maxRequests;
    Set<Long> acceptedApiTypes;
    String rolePlan;
    Boolean isAdditionalPlan;
}