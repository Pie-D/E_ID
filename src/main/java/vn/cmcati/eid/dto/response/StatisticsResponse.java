package vn.cmcati.eid.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticsResponse {
    private long totalRequests;
    private long ocrRequests;
    private long faceMatchingRequests;
    private long ekycRequests;
    private long successfulRequests;
    private long failedRequests;
    private BigDecimal avgOcrResponseTime;
    private BigDecimal avgFaceMatchingResponseTime;
    private BigDecimal avgEkycResponseTime;
}
