package vn.cmcati.eid.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "api_type")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "apiType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApiRequest> apiRequests = new ArrayList<>();

    @ManyToMany(mappedBy = "acceptedApiTypes")
    private List<PaymentPlan> paymentPlans = new ArrayList<>();


}
