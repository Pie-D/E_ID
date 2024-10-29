package vn.cmcati.eid.mapper;

import org.mapstruct.Mapper;

import vn.cmcati.eid.dto.request.PaymentHistoryRequest;
import vn.cmcati.eid.dto.response.PaymentHistoryResponse;
import vn.cmcati.eid.entity.PaymentHistory;

@Mapper(componentModel = "spring")
public interface PaymentHistoryMapper {
    PaymentHistory toPaymentHistory(PaymentHistoryRequest paymentHistoryRequest);
    PaymentHistoryResponse toPaymentHistoryResponse(PaymentHistory paymentHistory);
}
