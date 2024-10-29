package vn.cmcati.eid.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.cmcati.eid.dto.request.UserPaymentRequest;
import vn.cmcati.eid.dto.request.UserPaymentUpdateRequest;
import vn.cmcati.eid.dto.response.ApiResponse;
import vn.cmcati.eid.dto.response.UserPaymentResponse;
import vn.cmcati.eid.service.UserPaymentService;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/userPayment")
public class UserPaymentController {
    UserPaymentService userPaymentService;

    @PostMapping("")
    public ApiResponse<UserPaymentResponse> createUserPayment(@RequestBody UserPaymentRequest userPaymentRequest) {
        return ApiResponse.<UserPaymentResponse>builder()
                .result(userPaymentService.createUserPayment(userPaymentRequest))
                .build();
    }
    @GetMapping("/user/{userId}")
    public ApiResponse<List<UserPaymentResponse>> getUserPaymentByUserId(@PathVariable Long userId) {
        return ApiResponse.<List<UserPaymentResponse>>builder()
                .result(userPaymentService.getUserPaymentByUserId(userId))
                .build();
    }
    @DeleteMapping("/{userPaymentId}")
    public ApiResponse<Boolean> deleteUserPayment(@PathVariable Long userPaymentId) {
        return ApiResponse.<Boolean>builder()
                .result(userPaymentService.deleteUserPaymentByUserPaymentId(userPaymentId))
                .build();
    }
    @GetMapping("/{userPaymentId}")
    public ApiResponse<UserPaymentResponse> getUserPaymentById(@PathVariable Long userPaymentId) {
        return ApiResponse.<UserPaymentResponse>builder()
                .result(userPaymentService.getUserPaymentById(userPaymentId))
                .build();
    }

    @PutMapping("/buy-requests")
    public ApiResponse<String> buyRequests(@RequestBody UserPaymentUpdateRequest userPaymentUpdateRequest) {
        if (userPaymentService.buyRequests(userPaymentUpdateRequest)) {
            return ApiResponse.<String>builder()
                    .result("success")
                    .build();
        }
        return ApiResponse.<String>builder()
                .result("failed")
                .build();
    }
}
