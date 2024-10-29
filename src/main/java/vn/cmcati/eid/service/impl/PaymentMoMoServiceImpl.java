package vn.cmcati.eid.service.impl;

import org.springframework.stereotype.Service;
import vn.cmcati.eid.config.Environment;
import vn.cmcati.eid.dto.request.PaymentRequestDto;
import vn.cmcati.eid.dto.response.PaymentResponseDto;
import vn.cmcati.eid.models.PaymentResponse;
import vn.cmcati.eid.models.QueryStatusTransactionResponse;
import vn.cmcati.eid.enums.RequestType;
import vn.cmcati.eid.exception.MoMoException;
import vn.cmcati.eid.processer.CreateOrderMoMo;
import vn.cmcati.eid.processer.QueryTransactionStatus;
import vn.cmcati.eid.service.PaymentMoMoService;
import vn.cmcati.eid.utils.LogUtils;

import java.math.BigDecimal;
@Service
public class PaymentMoMoServiceImpl implements PaymentMoMoService {
    @Override
    public PaymentResponseDto paymentMoMo(PaymentRequestDto paymentDto) throws MoMoException {
        LogUtils.init();
        BigDecimal totalAmount = BigDecimal.valueOf(0);
        totalAmount = paymentDto.getPaymentPlanDto().getPrice();
        String orderId = String.valueOf(System.currentTimeMillis());
        String requestId = String.valueOf(System.currentTimeMillis());
        Environment environment = Environment.selectEnv("dev");
        PaymentResponse captureATMMoMoResponse = null;
        System.out.println(totalAmount.toString());
        try {
            captureATMMoMoResponse = CreateOrderMoMo.
                    process(environment, orderId, requestId, totalAmount.toString(), paymentDto.getContent(), paymentDto.getReturnUrl(),
                            paymentDto.getNotifyUrl(), "", RequestType.PAY_WITH_ATM, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("url ====: "+captureATMMoMoResponse.getPayUrl());

        return PaymentResponseDto.builder()
                .url(captureATMMoMoResponse.getPayUrl())
                .orderId(captureATMMoMoResponse.getOrderId())
                .requestId(captureATMMoMoResponse.getRequestId())
                .build();
    }
    public Boolean checkPayment(String orderId, String requestId) throws Exception{
        Environment environment = Environment.selectEnv("dev");
        QueryStatusTransactionResponse queryStatusTransactionResponse = QueryTransactionStatus.process(environment, orderId, requestId);
        System.out.println("StatusTransaction: "+queryStatusTransactionResponse.getMessage());
        if(queryStatusTransactionResponse.getResultCode() == 0){
            return true;
        }
        return false;
    }
}
