package vn.cmcati.eid.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.cmcati.eid.dto.request.PaymentMethodRequest;
import vn.cmcati.eid.dto.response.PaymentMethodResponse;
import vn.cmcati.eid.entity.PaymentMethod;
import vn.cmcati.eid.exception.AppException;
import vn.cmcati.eid.exception.ErrorCode;
import vn.cmcati.eid.mapper.PaymentMethodMapper;
import vn.cmcati.eid.repository.PaymentMethodRepository;
import vn.cmcati.eid.service.PaymentMethodService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentMethodServiceImpl  implements PaymentMethodService {

    PaymentMethodMapper paymentMethodMapper;
    PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethodResponse> getAllPaymentMethods() {
        return paymentMethodRepository.findAll().stream()
                .map(paymentMethodMapper :: toPaymentMethodResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentMethodResponse getPaymentMethodById(Long id) {
        return paymentMethodMapper.toPaymentMethodResponse(paymentMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND)));
    }

    @Override
    public PaymentMethodResponse createPaymentMethod(PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod paymentMethod = paymentMethodMapper.toPaymentMethod(paymentMethodRequest);
        return paymentMethodMapper.toPaymentMethodResponse(paymentMethodRepository.save(paymentMethod));
    }

    @Override
    public PaymentMethodResponse updatePaymentMethod(Long id, PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        paymentMethodMapper.updatePaymentMethod(paymentMethod, paymentMethodRequest);
        return paymentMethodMapper.toPaymentMethodResponse(paymentMethodRepository.save(paymentMethod));
    }

    @Override
    public boolean deletePaymentMethod(Long id) {
        paymentMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        paymentMethodRepository.deleteById(id);
        return true;
    }
}
