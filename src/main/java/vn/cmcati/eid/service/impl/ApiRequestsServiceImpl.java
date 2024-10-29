package vn.cmcati.eid.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmcati.eid.dto.response.ApiRequestResponse;
import vn.cmcati.eid.dto.response.StatisticsResponse;
import vn.cmcati.eid.entity.ApiRequest;
import vn.cmcati.eid.entity.ApiType;
import vn.cmcati.eid.entity.ResponseDataBase;
import vn.cmcati.eid.exception.AppException;
import vn.cmcati.eid.exception.ErrorCode;
import vn.cmcati.eid.mapper.ApiRequestMapper;
import vn.cmcati.eid.repository.ApiRequestsRepository;
import vn.cmcati.eid.repository.ApiTypeRepository;
import vn.cmcati.eid.service.ApiRequestsService;
import vn.cmcati.eid.utils.ApiRequestsUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApiRequestsServiceImpl implements ApiRequestsService {

    @Autowired
    private ApiRequestsRepository apiRequestsRepository;

    @Autowired
    private ApiTypeRepository apiTypeRepository;

    private ApiRequestMapper apiRequestMapper;

    private ApiRequestsUtils apiRequestsUtils = new ApiRequestsUtils();

    @Override
    public StatisticsResponse getStatistics() {
        StatisticsResponse.StatisticsResponseBuilder statisticsBuilder = StatisticsResponse.builder();

        long totalRequests = apiRequestsRepository.countTotalRequests();
        statisticsBuilder.totalRequests(totalRequests);

        ApiType apiOCR = apiTypeRepository.findByNameIgnoreCase("OCR");
        ApiType apiFaceMatching = apiTypeRepository.findByNameIgnoreCase("face-matching");
        ApiType apiEKYC = apiTypeRepository.findByNameIgnoreCase("eKYC");

        long ocrRequests = apiRequestsRepository.countByApiType(apiOCR);
        long faceMatchingRequests = apiRequestsRepository.countByApiType(apiFaceMatching);
        long ekycRequests = apiRequestsRepository.countByApiType(apiEKYC);
        statisticsBuilder.ocrRequests(ocrRequests);
        statisticsBuilder.faceMatchingRequests(faceMatchingRequests);
        statisticsBuilder.ekycRequests(ekycRequests);

        long successfulRequests = apiRequestsRepository.countByStatus("SUCCESS");
        long failedRequests = apiRequestsRepository.countByStatus("FAIL");
        statisticsBuilder.successfulRequests(successfulRequests);
        statisticsBuilder.failedRequests(failedRequests);

        BigDecimal avgOcrResponseTime = apiRequestsRepository.findAverageResponseTimeByApiType(apiOCR);
        BigDecimal avgFaceMatchingResponseTime = apiRequestsRepository.findAverageResponseTimeByApiType(apiFaceMatching);
        BigDecimal avgEKYCResponseTime = apiRequestsRepository.findAverageResponseTimeByApiType(apiEKYC);
        statisticsBuilder.avgOcrResponseTime(avgOcrResponseTime);
        statisticsBuilder.avgFaceMatchingResponseTime(avgFaceMatchingResponseTime);
        statisticsBuilder.avgEkycResponseTime(avgEKYCResponseTime);

        return statisticsBuilder.build();
    }

    @Override
    public List<ApiRequestResponse> getApiRequests() {
        List<ApiRequest> apiRequest = apiRequestsRepository.findAll();
        return apiRequest.stream().map(apiRequestMapper::toApiRequestResponse).collect(Collectors.toList());
    }

    @Override
    public List<ApiRequestResponse> getApiRequestsByType(String type) {
        return apiRequestsRepository.findByApiTypeName(type).stream().map(apiRequestMapper::toApiRequestResponse).collect(Collectors.toList());
    }

    @Override
    public List<ApiRequestResponse> getApiRequestsByUserId(Long userId) {
        return apiRequestsRepository.findByUserId(userId).stream().map(apiRequestMapper::toApiRequestResponse).collect(Collectors.toList());
    }

    @Override
    public List<ApiRequestResponse> getApiRequestByTimes(Instant start, Instant end) {
        return apiRequestsRepository.findApiRequestsByTimes(start, end).stream().map(apiRequestMapper::toApiRequestResponse).collect(Collectors.toList());
    }

    @Override
    public JsonNode getResponseDataByEventID(String eventID) {
        List<ApiRequest> apiRequests = apiRequestsRepository.findByEventId(eventID);
        ObjectMapper objectMapper = new ObjectMapper();

        if (apiRequests.isEmpty()) {
            return null;
        }

        List<ResponseDataBase> responseDataList = apiRequests.stream()
                .map(ApiRequest::getResponseDataBase)
                .collect(Collectors.toList());
        List<JsonNode> responseDataJsonList = new ArrayList<>();

        for (ResponseDataBase responseDataBase : responseDataList) {
            responseDataJsonList.add(objectMapper.valueToTree(responseDataBase));
        }

        try {
            return apiRequestsUtils.mergeJsonList(responseDataJsonList);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
