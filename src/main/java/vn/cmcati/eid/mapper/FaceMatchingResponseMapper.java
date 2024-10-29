package vn.cmcati.eid.mapper;

import org.mapstruct.Mapper;
import vn.cmcati.eid.dto.response.FaceMatchingResponse;
import vn.cmcati.eid.entity.FaceMatchingResponseData;

@Mapper(componentModel = "spring")
public interface FaceMatchingResponseMapper {
    FaceMatchingResponseData toFaceMatchingResponseData(FaceMatchingResponse faceMatchingResponse);
    FaceMatchingResponse toFaceMatchingResponse(FaceMatchingResponseData faceMatchingResponseData);
}
