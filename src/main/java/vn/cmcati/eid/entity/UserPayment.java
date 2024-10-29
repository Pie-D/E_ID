package vn.cmcati.eid.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "user_payments")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPayment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    PaymentPlan paymentPlan;

    @Column(name = "payment_date")
    Instant paymentDate;

    @Column(name = "remaining_requests", nullable = false)
    Integer remainingRequests;

    @OneToMany(mappedBy = "userPayment", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    List<PaymentHistory> paymentHistory;


    @OneToOne(mappedBy = "userPayment",cascade = CascadeType.ALL)
    Token token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    PaymentMethod scheduledPaymentMethod;

}