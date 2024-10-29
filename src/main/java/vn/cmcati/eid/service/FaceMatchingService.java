package vn.cmcati.eid.service;

import org.springframework.web.multipart.MultipartFile;
import vn.cmcati.eid.dto.response.FaceMatchingResponse;

public interface FaceMatchingService {
    FaceMatchingResponse performFaceMatching(MultipartFile cardImage, MultipartFile faceImage, String eventId, String apiKey);
}
