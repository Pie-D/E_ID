package vn.cmcati.eid.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.cmcati.eid.dto.request.PaymentHistoryRequest;
import vn.cmcati.eid.dto.response.PaymentHistoryResponse;
import vn.cmcati.eid.entity.PaymentHistory;
import vn.cmcati.eid.exception.AppException;
import vn.cmcati.eid.exception.ErrorCode;
import vn.cmcati.eid.mapper.PaymentHistoryMapper;
import vn.cmcati.eid.repository.PaymentHistoryRepository;
import vn.cmcati.eid.service.PaymentHistoryService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class PaymentHistoryServiceImpl implements PaymentHistoryService {

    PaymentHistoryRepository paymentHistoryRepository;
    PaymentHistoryMapper paymentHistoryMapper;
    @Override
    public PaymentHistoryResponse createPaymentHistory(PaymentHistoryRequest request) {

        PaymentHistory paymentHistory = paymentHistoryMapper.toPaymentHistory(request);
        paymentHistory.setCreateTime(Instant.now());
        return paymentHistoryMapper.toPaymentHistoryResponse(paymentHistoryRepository.save(paymentHistory));

    }

    @Override
    public PaymentHistoryResponse getPaymentHistoryByPaymentHistoryId(Long id) {
        PaymentHistory paymentHistory = paymentHistoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND)
        );
        return paymentHistoryMapper.toPaymentHistoryResponse(paymentHistory);
    }

    @Override
    public List<PaymentHistoryResponse> getPaymentHistoryByUserPaymentId(Long userPaymentId) {

        return paymentHistoryRepository.findPaymentHistoriesByUserPaymentId(userPaymentId).stream().map(paymentHistoryMapper :: toPaymentHistoryResponse).collect(Collectors.toList());
    }

}
