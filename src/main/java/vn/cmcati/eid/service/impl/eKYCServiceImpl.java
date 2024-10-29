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
import vn.cmcati.eid.dto.response.EKYCResponse;
import vn.cmcati.eid.entity.ApiType;
import vn.cmcati.eid.entity.EKYCResponseData;
import vn.cmcati.eid.entity.User;
import vn.cmcati.eid.enums.ApiCallStatus;
import vn.cmcati.eid.exception.AppException;
import vn.cmcati.eid.exception.ErrorCode;
import vn.cmcati.eid.mapper.eKYCResponseMapper;
import vn.cmcati.eid.repository.*;
import vn.cmcati.eid.service.eKYCService;
import vn.cmcati.eid.utils.ApiServiceUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class eKYCServiceImpl implements eKYCService {

    @Value("${api.token}")
    @NonFinal
    String authorizationToken;

    ApiRequestsRepository apiRequestsRepository;

    ApiTypeRepository apiTypeRepository;
    TokenRepository tokenRepository;
    UserPaymentRepository userPaymentRepository;

    UserServiceImpl userService;
    eKYCResponseDataRepository eKYCResponseDataRepository;
    eKYCResponseMapper eKYCResponseMapper;

    @Override
    public EKYCResponse performEKYC(MultipartFile cardImage, MultipartFile faceImage, String eventId, String apiKey) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ApiType apiType = apiTypeRepository.findByNameIgnoreCase("eKYC");

        User user = userService.getCurrentUser();

        ApiServiceUtils apiServiceUtils = new ApiServiceUtils(apiRequestsRepository, userPaymentRepository, tokenRepository);

        try {
            HttpHeaders headers = apiServiceUtils.createHeaders(authorizationToken);

            MultipartFile[] images = new MultipartFile[]{cardImage, faceImage};
            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    apiServiceUtils.getMultiValueMapHttpEntity(images, headers);

            String eKYCApiUrl = "https://ekyc.cmcati.vn/api/ekyc/production";
            ResponseEntity<String> response = apiServiceUtils.postRequest(eKYCApiUrl, requestEntity);
            stopWatch.stop();

            JsonNode jsonResponse = apiServiceUtils.parseResponse(response);

            EKYCResponse eKYCResponse = EKYCResponse.builder()
                    .address(jsonResponse.path("address").asText(null))
                    .backImage(jsonResponse.path("backImage").asText(null))
                    .birthDay(jsonResponse.path("birthDay").asInt(0))
                    .birthMonth(jsonResponse.path("birthMonth").asInt(0))
                    .birthString(jsonResponse.path("birthString").asText(null))
                    .birthYear(jsonResponse.path("birthYear").asInt(0))
                    .cardCroppedImage(jsonResponse.path("cardCroppedImage").asText(null))
                    .cardHeight(jsonResponse.path("cardHeight").asInt(0))
                    .cardImage(jsonResponse.path("cardImage").asText(null))
                    .cardNo(jsonResponse.path("cardNo").asText(null))
                    .cardOcrBlurs(jsonResponse.path("cardOcrBlurs").asText(null))
                    .cardOcrCut(jsonResponse.path("cardOcrCut").asDouble(0.0))
                    .cardOcrDevice(jsonResponse.path("cardOcrDevice").asDouble(0.0))
                    .cardOcrGlare(jsonResponse.path("cardOcrGlare").asDouble(0.0))
                    .cardOcrOcclusion(jsonResponse.path("cardOcrOcclusion").asDouble(0.0))
                    .cardOcrPrint(jsonResponse.path("cardOcrPrint").asInt(0))
                    .cardWidth(jsonResponse.path("cardWidth").asInt(0))
                    .dacDiem(jsonResponse.path("dacdiem").asText(null))
                    .danToc(jsonResponse.path("danToc").asText(null))
                    .expiredDate(jsonResponse.path("expiredDate").asText(null))
                    .faceCroppedImage(jsonResponse.path("faceCroppedImage").asText(null))
                    .faceImage(jsonResponse.path("faceImage").asText(null))
                    .fullName(jsonResponse.path("fullName").asText(null))
                    .gender(jsonResponse.path("gender").asText(null))
                    .hang(jsonResponse.path("hang").asText(null))
                    .idCccd(jsonResponse.path("id").asText(null))
                    .idCccdHc(jsonResponse.path("id_cccd_hc").asText(null))
                    .idHc(jsonResponse.path("id_hc").asText(null))
                    .loaiHc(jsonResponse.path("loai_hc").asText(null))
                    .masoHc(jsonResponse.path("maso_hc").asText(null))
                    .mrz(jsonResponse.path("mrz").asText(null))
                    .nationality(jsonResponse.path("nationality").asText(null))
                    .ngayCapHc(jsonResponse.path("ngaycap_hc").asText(null))
                    .ngayHetHc(jsonResponse.path("ngayhet_hc").asText(null))
                    .ngayTrung(jsonResponse.path("ngaytrung").asText(null))
                    .noiCapBlx(jsonResponse.path("noicap_blx").asText(null))
                    .noiCapCccd(jsonResponse.path("noicap_cccd").asText(null))
                    .noiCapCmnd(jsonResponse.path("noicap_cmnd").asText(null))
                    .noiCapHc(jsonResponse.path("noicap_hc").asText(null))
                    .noiSinhHc(jsonResponse.path("noisinh_hc").asText(null))
                    .placeOfBirth(jsonResponse.path("placeOfBirth").asText(null))
                    .publishDate(jsonResponse.path("publishDate").asText(null))
                    .religion(jsonResponse.path("religion").asText(null))
                    .similarity(jsonResponse.path("similarity").asDouble(0.0))
                    .thuongTruBlx(jsonResponse.path("thuongtru_blx").asText(null))
                    .build();

            EKYCResponseData eKYCResponseData = eKYCResponseMapper.toEKYCResponseData(eKYCResponse);

            eKYCResponseDataRepository.save(eKYCResponseData);

            apiServiceUtils.saveApiRequest(user, apiType, eKYCResponseData, stopWatch.getTotalTimeSeconds(), ApiCallStatus.SUCCESS, eventId);
            apiServiceUtils.decreaseRemainingRequests(apiKey);

            return eKYCResponse;

        } catch (Exception e) {
            stopWatch.stop();

            apiServiceUtils.saveApiRequest(user, apiType, null, stopWatch.getTotalTimeSeconds(), ApiCallStatus.FAIL, eventId);
            throw new AppException(ErrorCode.EKYC_PROCESSING_ERROR);
        }
    }
}
