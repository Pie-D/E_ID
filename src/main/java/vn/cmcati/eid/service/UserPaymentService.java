package vn.cmcati.eid.service;

import vn.cmcati.eid.dto.request.UserPaymentRequest;
import vn.cmcati.eid.dto.request.UserPaymentUpdateRequest;
import vn.cmcati.eid.dto.response.UserPaymentResponse;
import java.util.List;

public interface UserPaymentService {
    UserPaymentResponse createUserPayment(UserPaymentRequest userPaymentRequest);
    List<UserPaymentResponse> getUserPaymentByUserId(Long userId);
    Boolean deleteUserPaymentByUserPaymentId(Long userPaymentId);
    UserPaymentResponse getUserPaymentById(Long userPaymentId);
    Boolean buyRequests(UserPaymentUpdateRequest userPaymentUpdateRequest);
}
