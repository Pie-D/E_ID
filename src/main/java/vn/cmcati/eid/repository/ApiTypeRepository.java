package vn.cmcati.eid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmcati.eid.entity.ApiType;
@Repository
public interface ApiTypeRepository extends JpaRepository<ApiType, Long> {
    ApiType findByNameIgnoreCase(String name);
}
