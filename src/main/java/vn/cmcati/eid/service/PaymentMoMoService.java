package vn.cmcati.eid.service;

import vn.cmcati.eid.dto.request.PaymentRequestDto;
import vn.cmcati.eid.dto.response.PaymentResponseDto;
import vn.cmcati.eid.models.PaymentResponse;
import vn.cmcati.eid.exception.MoMoException;

public interface PaymentMoMoService {
    PaymentResponseDto paymentMoMo(PaymentRequestDto payment) throws MoMoException;
    Boolean checkPayment(String orderId, String requestId) throws Exception;
}
