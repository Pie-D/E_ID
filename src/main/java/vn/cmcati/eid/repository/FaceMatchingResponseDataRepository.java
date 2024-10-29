package vn.cmcati.eid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmcati.eid.entity.FaceMatchingResponseData;

@Repository
public interface FaceMatchingResponseDataRepository extends JpaRepository<FaceMatchingResponseData, Long> {
}
