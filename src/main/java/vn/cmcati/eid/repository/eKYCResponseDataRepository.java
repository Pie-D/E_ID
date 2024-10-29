package vn.cmcati.eid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmcati.eid.entity.EKYCResponseData;

@Repository
public interface eKYCResponseDataRepository extends JpaRepository<EKYCResponseData, Long> {
}
