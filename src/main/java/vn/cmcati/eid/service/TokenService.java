package vn.cmcati.eid.service;

import vn.cmcati.eid.dto.request.TokenCreationRequest;
import vn.cmcati.eid.dto.response.TokenResponse;

public interface TokenService {
    TokenResponse createToken(TokenCreationRequest request);
    TokenResponse getTokenById(Long tokenId);
}
