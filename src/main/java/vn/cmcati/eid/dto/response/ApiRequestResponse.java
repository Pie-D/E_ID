package vn.cmcati.eid.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.cmcati.eid.entity.ResponseDataBase;

import java.math.BigDecimal;
import java.time.Instant;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiRequestResponse {
    private Long id;

    private UserResponse user;

    private ApiTypeResponse apiType;

    private ResponseDataBase responseDataBase;

    private BigDecimal responseTime;

    private String status;

    private Instant timestamp;

    private String eventId;
}
