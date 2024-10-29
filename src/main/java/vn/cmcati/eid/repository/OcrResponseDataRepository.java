package vn.cmcati.eid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmcati.eid.entity.OcrResponseData;

@Repository
public interface OcrResponseDataRepository extends JpaRepository<OcrResponseData, Long> {
}
