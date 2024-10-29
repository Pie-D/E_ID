package vn.cmcati.eid.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.cmcati.eid.dto.request.PaymentPlanRequest;
import vn.cmcati.eid.dto.response.ApiResponse;
import vn.cmcati.eid.dto.response.PaymentPlanResponse;
import vn.cmcati.eid.service.PaymentPlanService;

import java.util.List;

@RestController
@RequestMapping("/api/paymentPlan")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentPlanController {
    PaymentPlanService paymentPlanService;

    @GetMapping()
    ApiResponse<List<PaymentPlanResponse>> getPaymentPlans() {
        return ApiResponse.<List<PaymentPlanResponse>>builder()
                .result(paymentPlanService.getPaymentPlans())
                .build();
    }

    @GetMapping("/{paymentPlanId}")
    ApiResponse<PaymentPlanResponse> getPaymentPlanById(@PathVariable("paymentPlanId") Long paymentPlanId) {
        return ApiResponse.<PaymentPlanResponse>builder()
                .result(paymentPlanService.getPaymentPlanById(paymentPlanId))
                .build();
    }

    @GetMapping("/additional-plans")
    ApiResponse<List<PaymentPlanResponse>> getAdditionalPlans() {
        return ApiResponse.<List<PaymentPlanResponse>>builder()
                .result(paymentPlanService.getPaymentPlansByIsAdditionalPlan(true))
                .build();
    }

    @PostMapping()
    ApiResponse<PaymentPlanResponse> createPaymentPlan(@RequestBody PaymentPlanRequest paymentPlanRequest) {
        return ApiResponse.<PaymentPlanResponse>builder()
                .result(paymentPlanService.createPaymentPlan(paymentPlanRequest))
                .build();
    }

    @PutMapping("/{paymentPlanId}")
    ApiResponse<PaymentPlanResponse> updatePaymentPlan(@RequestBody PaymentPlanRequest paymentPlanRequest, @PathVariable("paymentPlanId") Long paymentPlanId) {
        return ApiResponse.<PaymentPlanResponse>builder()
                .result(paymentPlanService.updatePaymentPlan(paymentPlanId, paymentPlanRequest))
                .build();
    }

    @DeleteMapping("/{paymentPlanId}")
    ApiResponse<Boolean> deletePaymentPlan(@PathVariable("paymentPlanId") Long paymentPlanId) {
        return ApiResponse.<Boolean>builder()
                .result(paymentPlanService.deletePaymentPlan(paymentPlanId))
                .build();
    }
}
