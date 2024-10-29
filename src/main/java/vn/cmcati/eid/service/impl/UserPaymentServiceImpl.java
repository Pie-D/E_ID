package vn.cmcati.eid.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.cmcati.eid.dto.request.PaymentHistoryRequest;
import vn.cmcati.eid.dto.request.TokenCreationRequest;
import vn.cmcati.eid.dto.request.UserPaymentRequest;
import vn.cmcati.eid.dto.request.UserPaymentUpdateRequest;
import vn.cmcati.eid.dto.response.PaymentMethodResponse;
import vn.cmcati.eid.dto.response.TokenResponse;
import vn.cmcati.eid.dto.response.UserPaymentResponse;
import vn.cmcati.eid.entity.*;
import vn.cmcati.eid.exception.AppException;
import vn.cmcati.eid.exception.ErrorCode;
import vn.cmcati.eid.mapper.PaymentHistoryMapper;
import vn.cmcati.eid.mapper.PaymentMethodMapper;
import vn.cmcati.eid.mapper.TokenMapper;
import vn.cmcati.eid.mapper.UserPaymentMapper;
import vn.cmcati.eid.repository.*;
import vn.cmcati.eid.service.PaymentHistoryService;
import vn.cmcati.eid.service.TokenService;
import vn.cmcati.eid.service.UserPaymentService;
import vn.cmcati.eid.service.UserService;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserPaymentServiceImpl implements UserPaymentService {

    UserPaymentRepository userPaymentRepository;
    PaymentPlanRepository paymentPlanRepository;
    PaymentMethodRepository paymentMethodRepository;
    TokenService tokenService;
    UserService userService;
    PaymentHistoryService paymentHistoryService;

    UserPaymentMapper userPaymentMapper;
    @Override
    @Transactional
    public UserPaymentResponse createUserPayment(UserPaymentRequest userPaymentRequest) {
        PaymentPlan paymentPlan = paymentPlanRepository.findById(userPaymentRequest.getPaymentPlanId()).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND)
        );

        PaymentMethod paymentMethod = paymentMethodRepository.findById(userPaymentRequest.getPaymentMethodId()).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND)
        );


        User user = userService.getCurrentUser();

        UserPayment userPayment = UserPayment.builder()
                .scheduledPaymentMethod(paymentMethod)
                .user(user)
                .paymentPlan(paymentPlan)
                .paymentDate(Instant.now())
                .remainingRequests(paymentPlan.getMaxRequests())
                .build();
        UserPayment resultUserPayment = userPaymentRepository.save(userPayment);

        PaymentHistoryRequest paymentHistoryRequest = PaymentHistoryRequest.builder()
                .orderId(userPaymentRequest.getOrderId())
                .requestId(userPaymentRequest.getRequestId())
                .totalAmount(paymentPlan.getPrice())
                .paymentMethod(paymentMethod)
                .userPayment(userPayment)
                .build();

        paymentHistoryService.createPaymentHistory(paymentHistoryRequest);

        TokenCreationRequest token = TokenCreationRequest.builder()
                .userPayment(resultUserPayment)
                .userPayment(userPayment)
                .build();

        tokenService.createToken(token);

        return userPaymentMapper.toUserPaymentResponse(resultUserPayment);

    }

    @Override
    public List<UserPaymentResponse> getUserPaymentByUserId(Long userId) {
        return userPaymentRepository.findUserPaymentByUserId(userId).stream().map(userPaymentMapper :: toUserPaymentResponse).collect(Collectors.toList());
    }

    @Override
    public Boolean deleteUserPaymentByUserPaymentId(Long userPaymentId) {
        userPaymentRepository.deleteById(userPaymentId);
        return true;
    }

    @Override
    public UserPaymentResponse getUserPaymentById(Long userPaymentId) {
        return userPaymentMapper.toUserPaymentResponse(userPaymentRepository.findById(userPaymentId).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND)
        ));
    }

    @Override
    public Boolean buyRequests(UserPaymentUpdateRequest userPaymentUpdateRequest) {
        PaymentPlan paymentPlan = paymentPlanRepository.findById(userPaymentUpdateRequest.getPaymentPlanId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        userPaymentRepository.buyRemainingRequests(userPaymentUpdateRequest.getUserPaymentId(), paymentPlan.getMaxRequests());
        return true;
    }
}
