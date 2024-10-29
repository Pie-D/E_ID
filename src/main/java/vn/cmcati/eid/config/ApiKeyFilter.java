package vn.cmcati.eid.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.cmcati.eid.dto.response.TokenApiKeyResponse;
import vn.cmcati.eid.entity.Token;
import vn.cmcati.eid.exception.AppException;
import vn.cmcati.eid.exception.ErrorCode;
import vn.cmcati.eid.repository.TokenRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiKeyFilter extends OncePerRequestFilter {
    String API_KEY_HEADER = "APIKEY";
    TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String apiKey = request.getHeader(API_KEY_HEADER);
        if (apiKey == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        TokenApiKeyResponse token =  tokenRepository.findTokenByToken(apiKey).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_TOKEN)
        );

        if (token.getExpiresAt().isBefore(Instant.now())  || token.getActive() == 0) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (token.getRemainingRequests() <= 0) {
            throw new AppException(ErrorCode.OUT_OF_REQUEST);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludedPaths = {
                "/auth",
                "/api/admin",
                "/api/apitype",
                "/api/paymentMethod",
                "/api/paymentPlan",
                "/api/paymentHistory",
                "/api/users",
                "/api/userPayment",
                "/api/momo",
                "/swagger-ui",
                "/api-docs"

        };

        String requestPath = request.getServletPath();
        for (String path : excludedPaths) {
            if (requestPath.startsWith(path)) {
                return true;
            }
        }
        return false;
    }
}