package vn.cmcati.eid.service;

import org.springframework.web.multipart.MultipartFile;
import vn.cmcati.eid.dto.response.OcrResponse;

public interface OcrService {
    OcrResponse performOcr(MultipartFile cardImage, String eventId, String apiKey);

}
