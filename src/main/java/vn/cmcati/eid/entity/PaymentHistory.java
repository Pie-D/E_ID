package vn.cmcati.eid.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payment_history")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;
    @Column(name = "order_id", nullable = false)
    String orderId;
    @Column(name = "request_id", nullable = false)
    String requestId;
    @Column(name = "create_time", nullable = false)
    Instant createTime;
    @Column(name = "total_amount", nullable = false)
    BigDecimal totalAmount;
    @ManyToOne
    @JoinColumn(name = "user_payment_id", referencedColumnName = "id")
    UserPayment userPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    PaymentMethod paymentMethod;
}
