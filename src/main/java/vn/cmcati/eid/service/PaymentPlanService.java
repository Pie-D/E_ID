package vn.cmcati.eid.service;

import org.springframework.stereotype.Service;
import vn.cmcati.eid.dto.request.PaymentPlanRequest;
import vn.cmcati.eid.dto.response.PaymentPlanResponse;

import java.util.List;

@Service
public interface PaymentPlanService {
    PaymentPlanResponse createPaymentPlan(PaymentPlanRequest paymentPlanRequest);
    PaymentPlanResponse updatePaymentPlan(Long id, PaymentPlanRequest paymentPlanRequest);
    boolean deletePaymentPlan(Long id);
    List<PaymentPlanResponse> getPaymentPlans();
    PaymentPlanResponse getPaymentPlanById(Long id);
    List<PaymentPlanResponse> getPaymentPlansByIsAdditionalPlan(boolean isAdditionalPlan);
}
