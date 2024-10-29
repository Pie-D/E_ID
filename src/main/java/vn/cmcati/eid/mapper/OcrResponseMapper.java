package vn.cmcati.eid.mapper;

import org.mapstruct.Mapper;
import vn.cmcati.eid.dto.response.OcrResponse;
import vn.cmcati.eid.entity.OcrResponseData;

@Mapper(componentModel = "spring")
public interface OcrResponseMapper {
    OcrResponseData toOcrResponseData(OcrResponse ocrResponse);
    OcrResponse toOcrResponse(OcrResponseData ocrResponseData);
}
