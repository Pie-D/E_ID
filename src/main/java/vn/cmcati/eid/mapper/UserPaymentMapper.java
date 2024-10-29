package vn.cmcati.eid.mapper;

import org.mapstruct.Mapper;


import vn.cmcati.eid.dto.request.UserPaymentRequest;
import vn.cmcati.eid.dto.response.UserPaymentResponse;

import vn.cmcati.eid.entity.UserPayment;

@Mapper(componentModel = "spring")
public interface UserPaymentMapper {
    UserPayment toUserPayment(UserPaymentRequest userPaymentRequest);
    UserPaymentResponse toUserPaymentResponse(UserPayment userPayment);

}
