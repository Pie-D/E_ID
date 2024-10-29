package vn.cmcati.eid.mapper;

import org.mapstruct.Mapper;
import vn.cmcati.eid.dto.response.EKYCResponse;
import vn.cmcati.eid.entity.EKYCResponseData;

@Mapper(componentModel = "spring")
public interface eKYCResponseMapper {
    EKYCResponseData toEKYCResponseData(EKYCResponse eKYCResponse);

    EKYCResponse toEKYCResponse(EKYCResponseData eKYCResponseData);
}
