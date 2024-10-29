package vn.cmcati.eid.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.cmcati.eid.dto.response.ApiResponse;
import vn.cmcati.eid.dto.response.PaymentHistoryResponse;
import vn.cmcati.eid.service.PaymentHistoryService;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/paymentHistory")
public class PaymentHistoryController {

    PaymentHistoryService paymentHistoryService;

    @GetMapping("/userPayment/{userPaymentId}")
    public ApiResponse<List<PaymentHistoryResponse>> getUserPaymentHistoryByUserPaymentId(@PathVariable Long userPaymentId) {
        return ApiResponse.<List<PaymentHistoryResponse>>builder()
                .result(paymentHistoryService.getPaymentHistoryByUserPaymentId(userPaymentId))
                .build();
    }

    @GetMapping("/{paymentHistoryId}")
    public ApiResponse<PaymentHistoryResponse> getUserPaymentHistoryByPaymentHistoryId(@PathVariable Long paymentHistoryId) {
        return ApiResponse.<PaymentHistoryResponse>builder()
                .result(paymentHistoryService.getPaymentHistoryByPaymentHistoryId(paymentHistoryId))
                .build();
    }


}
