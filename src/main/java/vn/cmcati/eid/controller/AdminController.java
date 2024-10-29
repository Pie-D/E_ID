package vn.cmcati.eid.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.cmcati.eid.dto.response.ApiRequestResponse;
import vn.cmcati.eid.dto.response.ApiResponse;
import vn.cmcati.eid.dto.response.StatisticsResponse;
import vn.cmcati.eid.service.ApiRequestsService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    ApiRequestsService apiRequestsService;

    @GetMapping("/statistics")
    ApiResponse<StatisticsResponse> getStatistics() {
        return ApiResponse.<StatisticsResponse>builder()
                .code(HttpStatus.OK.value())
                .result(apiRequestsService.getStatistics())
                .build();
    }

    @GetMapping("/api_request")
    ApiResponse<List<ApiRequestResponse>> getApiRequests() {
        List<ApiRequestResponse> apiRequests = apiRequestsService.getApiRequests();

        return ApiResponse.<List<ApiRequestResponse>>builder()
                .result(apiRequests)
                .build();
    }

    @GetMapping("/api_request_by_type/{type}")
    ApiResponse<List<ApiRequestResponse>> getApiRequestByType(@PathVariable String type) {
        List<ApiRequestResponse> apiRequestResponses = apiRequestsService.getApiRequestsByType(type);
        return ApiResponse.<List<ApiRequestResponse>>builder()
                .result(apiRequestResponses)
                .build();
    }

    @GetMapping("/api_request_by_user/{userId}")
    ApiResponse<List<ApiRequestResponse>> getApiRequestByUserId(@PathVariable Long userId) {
        List<ApiRequestResponse> apiRequestResponses = apiRequestsService.getApiRequestsByUserId(userId);
        return ApiResponse.<List<ApiRequestResponse>>builder()
                .result(apiRequestResponses)
                .build();
    }
    @GetMapping("/api_request_by_time")
    ApiResponse<List<ApiRequestResponse>> getApiRequestByTime(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        Instant startInstant = start.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endInstant = end.atTime(23, 59, 59).toInstant(ZoneOffset.UTC);
        List<ApiRequestResponse> apiRequestResponses = apiRequestsService.getApiRequestByTimes(startInstant, endInstant);
        return ApiResponse.<List<ApiRequestResponse>>builder()
                .result(apiRequestResponses)
                .build();
    }
}
