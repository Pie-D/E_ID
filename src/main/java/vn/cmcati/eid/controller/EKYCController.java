package vn.cmcati.eid.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.cmcati.eid.dto.response.ApiResponse;
import vn.cmcati.eid.dto.response.EKYCResponse;
import vn.cmcati.eid.dto.response.FaceMatchingResponse;
import vn.cmcati.eid.dto.response.OcrResponse;
import vn.cmcati.eid.exception.AppException;
import vn.cmcati.eid.exception.ErrorCode;
import vn.cmcati.eid.service.ApiRequestsService;
import vn.cmcati.eid.service.FaceMatchingService;
import vn.cmcati.eid.service.OcrService;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api/eKYC")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
public class EKYCController {

    OcrService ocrService;
    FaceMatchingService faceMatchingService;
    vn.cmcati.eid.service.eKYCService eKYCService;
    private ApiRequestsService apiRequestsService;

    @PostMapping("/ocr")
    ApiResponse<OcrResponse> ocr(@RequestParam("cardImage") MultipartFile cardImage,
                                 @RequestParam("eventID") String eventID,
                                 HttpServletRequest request)
    {
        String apiKey = request.getHeader("APIKEY");
        ApiResponse<OcrResponse> apiResponse = new ApiResponse<>();
        OcrResponse ocrResponse = ocrService.performOcr(cardImage, eventID, apiKey);
        apiResponse.setResult(ocrResponse);
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(HttpStatus.OK.getReasonPhrase());
        return apiResponse;
    }

    @PostMapping("/face-matching")
    ApiResponse<FaceMatchingResponse> faceMatching(@RequestParam("cardImage") MultipartFile cardImage,
                                                   @RequestParam("faceImage") MultipartFile faceImage,
                                                   @RequestParam("eventID") String eventID,
                                                   HttpServletRequest request)
    {
        String apiKey = request.getHeader("APIKEY");
        ApiResponse<FaceMatchingResponse> apiResponse = new ApiResponse<>();
        FaceMatchingResponse faceMatchingResponse = faceMatchingService.performFaceMatching(cardImage, faceImage, eventID, apiKey);
        apiResponse.setResult(faceMatchingResponse);
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(HttpStatus.OK.getReasonPhrase());
        return apiResponse;
    }

    @PostMapping("/eKYC")
    ApiResponse<EKYCResponse> eKYC(@RequestParam("cardImage") MultipartFile cardImage,
                                   @RequestParam("faceImage") MultipartFile faceImage,
                                   @RequestParam("eventID") String eventID,
                                   HttpServletRequest request)
    {
        String apiKey = request.getHeader("APIKEY");
        ApiResponse<EKYCResponse> apiResponse = new ApiResponse<>();
        EKYCResponse eKYCResponse = eKYCService.performEKYC(cardImage, faceImage, eventID, apiKey);
        apiResponse.setResult(eKYCResponse);
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(HttpStatus.OK.getReasonPhrase());
        return apiResponse;
    }

    @GetMapping("/response-by-eventId")
    ApiResponse<JsonNode> getResponseByEventID(@RequestParam("eventID") String eventID) {
        ApiResponse<JsonNode> apiResponse = new ApiResponse<>();

        try {
            JsonNode responseData = apiRequestsService.getResponseDataByEventID(eventID);

            if (responseData == null) {
                apiResponse.setCode(ErrorCode.NOT_FOUND.getCode());
                apiResponse.setMessage(ErrorCode.NOT_FOUND.getMessage());
                apiResponse.setResult(null);
            } else {
                apiResponse.setCode(HttpStatus.OK.value());
                apiResponse.setMessage(HttpStatus.OK.getReasonPhrase());
                apiResponse.setResult(responseData);
            }
        } catch (AppException e) {
            apiResponse.setCode(e.getErrorCode().getCode());
            apiResponse.setMessage(e.getErrorCode().getMessage() + ": " + e.getMessage());
            apiResponse.setResult(null);
        } catch (Exception e) {
            apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
            apiResponse.setMessage("An unexpected error occurred: " + e.getMessage());
            apiResponse.setResult(null);
        }

        return apiResponse;
    }
}
