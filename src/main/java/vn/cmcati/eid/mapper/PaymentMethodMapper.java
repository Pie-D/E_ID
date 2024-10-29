package vn.cmcati.eid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.cmcati.eid.dto.request.PaymentMethodRequest;
import vn.cmcati.eid.dto.response.PaymentMethodResponse;
import vn.cmcati.eid.entity.PaymentMethod;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethod toPaymentMethod(PaymentMethodRequest paymentMethodRequest);
    PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod);
    void updatePaymentMethod(@MappingTarget PaymentMethod paymentMethod, PaymentMethodRequest paymentMethodRequest);
}
