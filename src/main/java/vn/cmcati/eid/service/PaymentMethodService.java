package vn.cmcati.eid.service;

import vn.cmcati.eid.dto.request.PaymentMethodRequest;
import vn.cmcati.eid.dto.response.PaymentMethodResponse;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethodResponse> getAllPaymentMethods();
    PaymentMethodResponse getPaymentMethodById(Long id);
    PaymentMethodResponse createPaymentMethod(PaymentMethodRequest paymentMethodRequest);
    PaymentMethodResponse updatePaymentMethod(Long id, PaymentMethodRequest paymentMethodRequest);
    boolean deletePaymentMethod(Long id);
}
