package vn.cmcati.eid.service;

import com.fasterxml.jackson.databind.JsonNode;
import vn.cmcati.eid.dto.response.ApiRequestResponse;
import vn.cmcati.eid.dto.response.EKYCResponse;
import vn.cmcati.eid.dto.response.StatisticsResponse;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface ApiRequestsService {
    StatisticsResponse getStatistics();
    List<ApiRequestResponse> getApiRequests();
    List<ApiRequestResponse> getApiRequestsByType(String type);
    List<ApiRequestResponse> getApiRequestsByUserId(Long userId);
    List<ApiRequestResponse> getApiRequestByTimes(Instant start, Instant end);
    JsonNode getResponseDataByEventID(String eventID);
}
