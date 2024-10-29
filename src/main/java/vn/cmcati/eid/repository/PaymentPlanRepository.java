package vn.cmcati.eid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmcati.eid.dto.response.PaymentPlanResponse;
import vn.cmcati.eid.entity.PaymentPlan;

import java.util.List;

@Repository
public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {
    List<PaymentPlan> findAllByIsAdditionalPlan(boolean isAdditionalPlan);
}
