package vn.cmcati.eid.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.cmcati.eid.dto.request.TokenCreationRequest;
import vn.cmcati.eid.dto.response.TokenResponse;
import vn.cmcati.eid.entity.PaymentPlan;
import vn.cmcati.eid.entity.Token;
import vn.cmcati.eid.entity.User;
import vn.cmcati.eid.enums.Role;
import vn.cmcati.eid.exception.AppException;
import vn.cmcati.eid.exception.ErrorCode;
import vn.cmcati.eid.mapper.TokenMapper;
import vn.cmcati.eid.repository.PaymentPlanRepository;
import vn.cmcati.eid.repository.TokenRepository;
import vn.cmcati.eid.repository.UserRepository;
import vn.cmcati.eid.service.TokenService;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class TokenServiceImpl  implements TokenService {


    TokenRepository tokenRepository;
    TokenMapper tokenMapper;

    @Override
    public TokenResponse createToken(TokenCreationRequest request) {
                String token = generateTokenByPaymentPlan();
        LocalDateTime expirationDateTime = LocalDateTime.now().plusMonths(1);
        Instant expirationInstant = expirationDateTime.atZone(ZoneId.systemDefault()).toInstant();
                Token tokenEntity = Token.builder()
                        .createdAt(Instant.now())
                        .token(token)
                        .expiresAt(expirationInstant)
                        .userPayment(request.getUserPayment())
                        .active(1)
                        .build();
        Token results = tokenRepository.save(tokenEntity);
        return tokenMapper.toTokenResponse(tokenEntity);
    }


    @Override
    public TokenResponse getTokenById(Long tokenId) {
        return tokenMapper.toTokenResponse(tokenRepository.findById(tokenId).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND)
        ));
    }



    private String generateTokenByPaymentPlan() {
        return UUID.randomUUID().toString();
    }
}
