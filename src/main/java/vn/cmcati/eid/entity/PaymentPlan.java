package vn.cmcati.eid.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payment_plans")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentPlan {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "max_requests", nullable = false)
    private Integer maxRequests;

    @Column(name = "is_additional_plan")
    private Boolean isAdditionalPlan;

    @OneToMany(mappedBy = "paymentPlan")
    private List<UserPayment> userPayments = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "accepted_api_type",
            joinColumns = @JoinColumn(name = "payment_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "api_type_id"))
    private List<ApiType> acceptedApiTypes = new ArrayList<>();

    @Column(name = "role_plan", nullable = false)
    private String rolePlan;


}