package vn.cmcati.eid.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import vn.cmcati.eid.entity.ApiRequest;
import vn.cmcati.eid.entity.ApiType;
import vn.cmcati.eid.entity.ResponseDataBase;
import vn.cmcati.eid.entity.User;
import vn.cmcati.eid.enums.ApiCallStatus;
import vn.cmcati.eid.repository.ApiRequestsRepository;
import vn.cmcati.eid.repository.TokenRepository;
import vn.cmcati.eid.repository.UserPaymentRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;


public class ApiServiceUtils {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private final ApiRequestsRepository apiRequestsRepository;
    private final UserPaymentRepository userPaymentRepository;
    private final TokenRepository tokenRepository;

    public ApiServiceUtils(ApiRequestsRepository apiRequestsRepository, UserPaymentRepository userPaymentRepository, TokenRepository tokenRepository) {
        this.apiRequestsRepository = apiRequestsRepository;
        this.userPaymentRepository = userPaymentRepository;
        this.tokenRepository = tokenRepository;
    }

    public HttpEntity<MultiValueMap<String, Object>> getMultiValueMapHttpEntity(MultipartFile[] images, HttpHeaders headers) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (MultipartFile image : images) {
            ByteArrayResource byteArrayResource = new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            };
            body.add(image.getName(), byteArrayResource);
        }
        return new HttpEntity<>(body, headers);
    }

    public HttpHeaders createHeaders(String authorizationToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Authorization", authorizationToken);
        return headers;
    }

    public JsonNode parseResponse(ResponseEntity<String> response) throws IOException {
        return mapper.readTree(response.getBody());
    }

    public ResponseEntity<String> postRequest(String apiUrl, HttpEntity<MultiValueMap<String, Object>> requestEntity) {
        return restTemplate.postForEntity(apiUrl, requestEntity, String.class);
    }

    public void saveApiRequest(User user, ApiType apiType, ResponseDataBase responseData, double responseTime, ApiCallStatus status, String eventId) {
        ApiRequest apiRequest = ApiRequest.builder()
                .user(user)
                .apiType(apiType)
                .responseDataBase(responseData)
                .responseTime(BigDecimal.valueOf(responseTime))
                .status(status.name())
                .timestamp(Instant.now())
                .eventId(eventId)
                .build();

        apiRequestsRepository.save(apiRequest);
    }

    public void decreaseRemainingRequests(String apiKey) {
        Long userPaymentId = tokenRepository.findTokenByToken(apiKey).get().getUserPaymentId();
        userPaymentRepository.decreaseRemainingRequests(userPaymentId);
    }
}
