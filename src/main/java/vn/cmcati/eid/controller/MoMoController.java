package vn.cmcati.eid.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.cmcati.eid.dto.request.PaymentRequestDto;
import vn.cmcati.eid.dto.response.ApiResponse;
import vn.cmcati.eid.dto.response.PaymentResponseDto;
import vn.cmcati.eid.exception.MoMoException;
import vn.cmcati.eid.service.PaymentMoMoService;


@RestController
@RequestMapping("/api/momo")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class MoMoController {

    PaymentMoMoService paymentMoMoService;

    @PostMapping("/payment")
    public ApiResponse<PaymentResponseDto> payment(@RequestBody PaymentRequestDto paymentDto) throws MoMoException {
        PaymentResponseDto paymentMoMo = paymentMoMoService.paymentMoMo(paymentDto);
        return ApiResponse.<PaymentResponseDto>builder()
                .result(paymentMoMo)
                .build();

    }
    @GetMapping("/checkPayment")
    public ApiResponse<Boolean> checkPayment(@RequestParam("orderId") String orderId, @RequestParam("requestId") String requestId) throws Exception {
        return ApiResponse.<Boolean>builder()
                .result(paymentMoMoService.checkPayment(orderId, requestId))
                .build();
    }
}
