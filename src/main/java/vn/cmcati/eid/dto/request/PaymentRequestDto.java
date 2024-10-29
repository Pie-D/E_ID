package vn.cmcati.eid.dto.request;

import lombok.Data;

@Data
public class PaymentRequestDto {
    private String content;
    private String returnUrl;
    private String notifyUrl;
    private PaymentPlanDto paymentPlanDto;
}
