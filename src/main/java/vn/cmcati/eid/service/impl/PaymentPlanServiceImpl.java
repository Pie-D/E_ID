package vn.cmcati.eid.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.cmcati.eid.dto.request.PaymentPlanRequest;
import vn.cmcati.eid.dto.response.PaymentPlanResponse;
import vn.cmcati.eid.entity.ApiType;
import vn.cmcati.eid.entity.PaymentPlan;
import vn.cmcati.eid.exception.AppException;
import vn.cmcati.eid.exception.ErrorCode;
import vn.cmcati.eid.mapper.PaymentPlanMapper;
import vn.cmcati.eid.repository.ApiTypeRepository;
import vn.cmcati.eid.repository.PaymentPlanRepository;
import vn.cmcati.eid.service.PaymentPlanService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentPlanServiceImpl implements PaymentPlanService {
    PaymentPlanRepository paymentPlanRepository;
    PaymentPlanMapper paymentPlanMapper;
    ApiTypeRepository apiTypeRepository;
    @Override
    public PaymentPlanResponse createPaymentPlan(PaymentPlanRequest paymentPlanRequest) {
        PaymentPlan paymentPlan = paymentPlanMapper.toPaymentPlan(paymentPlanRequest);
        List<ApiType> apiTypes = apiTypeRepository.findAllById(paymentPlanRequest.getAcceptedApiTypes());
        paymentPlan.setAcceptedApiTypes(apiTypes);
        return paymentPlanMapper.toPaymentPlanResponse(paymentPlanRepository.save(paymentPlan));
    }

    @Override
    public PaymentPlanResponse updatePaymentPlan(Long id, PaymentPlanRequest paymentPlanRequest) {
        PaymentPlan paymentPlan = paymentPlanRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND));
        paymentPlanMapper.updatePaymentPlan(paymentPlan, paymentPlanRequest);
        return paymentPlanMapper.toPaymentPlanResponse(paymentPlanRepository.save(paymentPlan));
    }

    @Override
    public boolean deletePaymentPlan(Long id) {
        paymentPlanRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND));
        paymentPlanRepository.deleteById(id);
        return true;
    }

    @Override
    public List<PaymentPlanResponse> getPaymentPlans() {
        return paymentPlanRepository.findAll().stream().map(paymentPlanMapper :: toPaymentPlanResponse).collect(Collectors.toList());
    }

    @Override
    public PaymentPlanResponse getPaymentPlanById(Long paymentPlanId) {
        return paymentPlanMapper.toPaymentPlanResponse(paymentPlanRepository.findById(paymentPlanId).orElseThrow(() ->
                new AppException(ErrorCode.NOT_FOUND)));
    }

    @Override
    public List<PaymentPlanResponse> getPaymentPlansByIsAdditionalPlan(boolean isAdditionalPlan) {
        return paymentPlanRepository.findAllByIsAdditionalPlan(isAdditionalPlan).stream().map(paymentPlanMapper :: toPaymentPlanResponse).collect(Collectors.toList());
    }
}
