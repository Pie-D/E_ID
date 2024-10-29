package vn.cmcati.eid.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;
import vn.cmcati.eid.dto.response.FaceMatchingResponse;
import vn.cmcati.eid.entity.ApiType;
import vn.cmcati.eid.entity.FaceMatchingResponseData;
import vn.cmcati.eid.entity.User;
import vn.cmcati.eid.enums.ApiCallStatus;
import vn.cmcati.eid.exception.AppException;
import vn.cmcati.eid.exception.ErrorCode;
import vn.cmcati.eid.mapper.FaceMatchingResponseMapper;
import vn.cmcati.eid.repository.*;
import vn.cmcati.eid.service.FaceMatchingService;
import vn.cmcati.eid.utils.ApiServiceUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FaceMatchingServiceImpl implements FaceMatchingService {

    @Value("${api.token}")
    @NonFinal
    private String authorizationToken;

    ApiRequestsRepository apiRequestsRepository;
    ApiTypeRepository apiTypeRepository;
    TokenRepository tokenRepository;
    UserPaymentRepository userPaymentRepository;
    UserServiceImpl userService;
    FaceMatchingResponseDataRepository faceMatchingResponseDataRepository;
    FaceMatchingResponseMapper faceMatchingResponseMapper;

    @Override
    public FaceMatchingResponse performFaceMatching(MultipartFile cardImage, MultipartFile faceImage, String eventId, String apiKey) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ApiType apiType = apiTypeRepository.findByNameIgnoreCase("face-matching");

        User user = userService.getCurrentUser();

        ApiServiceUtils apiServiceUtils = new ApiServiceUtils(apiRequestsRepository, userPaymentRepository, tokenRepository);

        try {
            HttpHeaders headers = apiServiceUtils.createHeaders(authorizationToken);

            MultipartFile[] images = new MultipartFile[]{cardImage, faceImage};
            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    apiServiceUtils.getMultiValueMapHttpEntity(images, headers);

            String faceMatchingApiUrl = "https://ekyc.cmcati.vn/api/face-matching-mqtt/production";
            ResponseEntity<String> response = apiServiceUtils.postRequest(faceMatchingApiUrl, requestEntity);
            stopWatch.stop();

            JsonNode jsonResponse = apiServiceUtils.parseResponse(response);
            FaceMatchingResponse faceMatchingResponse = FaceMatchingResponse.builder()
                    .similarity(jsonResponse.path("similarity").asDouble(0.0))
                    .build();

            FaceMatchingResponseData faceMatchingResponseData = faceMatchingResponseMapper.toFaceMatchingResponseData(faceMatchingResponse);
            faceMatchingResponseDataRepository.save(faceMatchingResponseData);

            apiServiceUtils.saveApiRequest(user, apiType, faceMatchingResponseData, stopWatch.getTotalTimeSeconds(), ApiCallStatus.SUCCESS, eventId);
            apiServiceUtils.decreaseRemainingRequests(apiKey);

            return faceMatchingResponse;

        } catch (Exception e) {
            stopWatch.stop();

            apiServiceUtils.saveApiRequest(user, apiType, null, stopWatch.getTotalTimeSeconds(), ApiCallStatus.FAIL, eventId);
            throw new AppException(ErrorCode.FACE_MATCHING_PROCESSING_ERROR);
        }
    }
}
