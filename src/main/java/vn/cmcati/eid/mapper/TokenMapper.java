package vn.cmcati.eid.mapper;

import org.mapstruct.Mapper;
import vn.cmcati.eid.dto.response.TokenResponse;
import vn.cmcati.eid.entity.Token;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    TokenResponse toTokenResponse(Token token);
    Token toToken(TokenResponse tokenResponse);
}
