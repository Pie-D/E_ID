package vn.cmcati.eid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmcati.eid.entity.PaymentHistory;

import java.util.List;

@Repository
public interface PaymentHistoryRepository  extends JpaRepository<PaymentHistory, Long> {
    List<PaymentHistory> findPaymentHistoriesByUserPaymentId(Long userPaymentId);
}
