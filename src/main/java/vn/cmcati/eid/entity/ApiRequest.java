package vn.cmcati.eid.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "api_requests")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApiRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "api_type_id")
    @JsonIgnore
    private ApiType apiType;

    @ManyToOne
    @JoinColumn(name = "response_data_id")
    private ResponseDataBase responseDataBase;

    @Column(name = "response_time", precision = 10, scale = 2)
    private BigDecimal responseTime;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "timestamp")
    private Instant timestamp;

    @Column(name = "event_id")
    private String eventId;
}