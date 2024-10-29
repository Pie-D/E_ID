package vn.cmcati.eid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.cmcati.eid.entity.UserPayment;

import java.util.List;


@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {

    List<UserPayment> findUserPaymentByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE UserPayment u SET u.remainingRequests = u.remainingRequests - 1 WHERE u.id = :UserPaymentId")
    void decreaseRemainingRequests(@Param("UserPaymentId") Long UserPaymentId);

    @Modifying
    @Transactional
    @Query("UPDATE UserPayment u SET u.remainingRequests = u.remainingRequests + :boughtRequests WHERE u.id = :UserPaymentId")
    void buyRemainingRequests(@Param("UserPaymentId") Long UserPaymentId, @Param("boughtRequests") int boughtRequests);
}
