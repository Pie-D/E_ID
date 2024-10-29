package vn.cmcati.eid.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.cmcati.eid.dto.request.PaymentMethodRequest;
import vn.cmcati.eid.dto.response.ApiResponse;
import vn.cmcati.eid.dto.response.PaymentMethodResponse;
import vn.cmcati.eid.service.PaymentMethodService;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/paymentMethod")
public class PaymentMethodController {

    PaymentMethodService paymentMethodService;

    @GetMapping
    ApiResponse<List<PaymentMethodResponse>> getAllPaymentMethods() {
        return ApiResponse.<List<PaymentMethodResponse>>builder()
                .result(paymentMethodService.getAllPaymentMethods())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<PaymentMethodResponse> getPaymentMethodById(@PathVariable("id") Long id) {
        return ApiResponse.<PaymentMethodResponse>builder()
                .result(paymentMethodService.getPaymentMethodById(id))
                .build();
    }

    @PostMapping
    ApiResponse<PaymentMethodResponse> createPaymentMethod(@RequestBody PaymentMethodRequest paymentMethodRequest) {
        return ApiResponse.<PaymentMethodResponse>builder()
                .result(paymentMethodService.createPaymentMethod(paymentMethodRequest))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<PaymentMethodResponse> updatePaymentMethod(@RequestBody PaymentMethodRequest paymentMethodRequest, @PathVariable("id") Long id) {
        return ApiResponse.<PaymentMethodResponse>builder()
                .result(paymentMethodService.updatePaymentMethod(id, paymentMethodRequest))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Boolean> deletePaymentMethod(@PathVariable("id") Long id) {
        return ApiResponse.<Boolean>builder()
                .result(paymentMethodService.deletePaymentMethod(id))
                .build();
    }
}
