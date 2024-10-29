package vn.cmcati.eid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.cmcati.eid.dto.request.ApiTypeRequest;
import vn.cmcati.eid.dto.response.ApiRequestResponse;
import vn.cmcati.eid.entity.ApiRequest;


@Mapper(componentModel = "spring")
public interface ApiRequestMapper {
    ApiRequest toApiRequest(ApiRequestResponse apiRequestResponse);
    ApiRequestResponse toApiRequestResponse(ApiRequest apiRequest);
}
