package vn.cmcati.eid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.cmcati.eid.dto.request.ApiTypeRequest;
import vn.cmcati.eid.dto.response.ApiTypeResponse;
import vn.cmcati.eid.entity.ApiType;

@Mapper(componentModel = "spring")
public interface ApiTypeMapper {
    ApiType toApiType(ApiTypeResponse apiType);
    ApiTypeResponse toApiTypeResponse(ApiType apiType);
    ApiType toApiType(ApiTypeRequest apiTypeRequest);
    void updateApiType(@MappingTarget ApiType apiType, ApiTypeRequest apiTypeRequest);
}
