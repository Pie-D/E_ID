package vn.cmcati.eid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.cmcati.eid.dto.response.TokenApiKeyResponse;
import vn.cmcati.eid.entity.Token;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("select new vn.cmcati.eid.dto.response.TokenApiKeyResponse(t.token, t.active, t.expiresAt, t.userPayment.remainingRequests, t.userPayment.id) from Token t where t.token = :token")
    Optional<TokenApiKeyResponse> findTokenByToken(@Param("token") String token);
}
