package vn.cmcati.eid.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "payment_methods")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "method", nullable = false)
    private String method;

    @OneToMany(mappedBy = "paymentMethod")
    private List<PaymentHistory> paymentHistory;

    @OneToMany(mappedBy = "scheduledPaymentMethod")
    private List<UserPayment> userPayments;
}
