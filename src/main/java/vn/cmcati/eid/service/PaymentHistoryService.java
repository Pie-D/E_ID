package vn.cmcati.eid.service;

import vn.cmcati.eid.dto.request.PaymentHistoryRequest;
import vn.cmcati.eid.dto.response.PaymentHistoryResponse;

import java.util.List;

public interface PaymentHistoryService {
    PaymentHistoryResponse createPaymentHistory(PaymentHistoryRequest request);
    PaymentHistoryResponse getPaymentHistoryByPaymentHistoryId(Long Id);
    List<PaymentHistoryResponse> getPaymentHistoryByUserPaymentId(Long UserPaymentId);
}
