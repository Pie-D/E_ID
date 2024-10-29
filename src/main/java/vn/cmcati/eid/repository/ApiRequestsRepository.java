package vn.cmcati.eid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.cmcati.eid.entity.ApiRequest;
import vn.cmcati.eid.entity.ApiType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface ApiRequestsRepository extends JpaRepository<ApiRequest, Long> {
    @Query("SELECT COUNT(ar) FROM ApiRequest ar")
    long countTotalRequests();

    long countByApiType(ApiType apiType);

    List<ApiRequest> findByApiTypeName(String apiTypeName);

    long countByStatus(String status);

    @Query("SELECT AVG(ar.responseTime) FROM ApiRequest ar WHERE ar.apiType = ?1")
    BigDecimal findAverageResponseTimeByApiType(ApiType apiType);

    List<ApiRequest> findByUserId(Long userId);

    @Query("SELECT ar FROM ApiRequest ar WHERE ar.timestamp BETWEEN :start AND :end")
    List<ApiRequest> findApiRequestsByTimes(@Param("start") Instant start, @Param("end") Instant end);

    List<ApiRequest> findByEventId(String eventId);
}
