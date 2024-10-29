package vn.cmcati.eid.service;

import org.springframework.web.multipart.MultipartFile;
import vn.cmcati.eid.dto.response.EKYCResponse;

public interface eKYCService {
    EKYCResponse performEKYC(MultipartFile cardImage, MultipartFile faceImage, String eventId, String apiKey);
}
