package vn.cmcati.eid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.cmcati.eid.dto.request.PaymentPlanRequest;
import vn.cmcati.eid.dto.response.PaymentPlanResponse;
import vn.cmcati.eid.entity.PaymentPlan;

@Mapper(componentModel = "spring")
public interface PaymentPlanMapper {
    PaymentPlanResponse toPaymentPlanResponse(PaymentPlan paymentPlan);
    @Mapping(target = "acceptedApiTypes", ignore = true)
    PaymentPlan toPaymentPlan(PaymentPlanRequest paymentPlanRequest);
    @Mapping(target = "acceptedApiTypes", ignore = true)
    void updatePaymentPlan(@MappingTarget PaymentPlan paymentPlan, PaymentPlanRequest paymentPlanRequest);

}
