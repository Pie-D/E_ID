package vn.cmcati.eid.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.cmcati.eid.dto.request.ApiTypeRequest;
import vn.cmcati.eid.dto.response.ApiTypeResponse;
import vn.cmcati.eid.entity.ApiType;
import vn.cmcati.eid.exception.AppException;
import vn.cmcati.eid.exception.ErrorCode;
import vn.cmcati.eid.mapper.ApiTypeMapper;
import vn.cmcati.eid.repository.ApiTypeRepository;
import vn.cmcati.eid.service.ApiTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiTypeServiceImpl implements ApiTypeService {
    ApiTypeRepository apiTypeRepository;
    ApiTypeMapper apiTypeMapper;
    @Override
    public List<ApiTypeResponse> getApiTypes() {
        return apiTypeRepository.findAll().stream().map(apiTypeMapper :: toApiTypeResponse).collect(Collectors.toList());
    }

    @Override
    public ApiTypeResponse addApiType(ApiTypeRequest apiTypeRequest) {
        ApiType apiType = apiTypeMapper.toApiType(apiTypeRequest);
        ApiType savedApiType = apiTypeRepository.save(apiType);
        return apiTypeMapper.toApiTypeResponse(savedApiType);
    }

    @Override
    public ApiTypeResponse updateApiType(Long id, ApiTypeRequest apiTypeRequest) {
        ApiType apiType = apiTypeRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND)
        );
        apiTypeMapper.updateApiType(apiType, apiTypeRequest);
        return apiTypeMapper.toApiTypeResponse(apiTypeRepository.save(apiType));
    }

    @Override
    public boolean deleteApiType(Long id) {
        ApiType apiType = apiTypeRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND)
        );
        apiTypeRepository.deleteById(id);
        return true;
    }

    @Override
    public ApiTypeResponse getApiType(Long id) {
        ApiType apiType = apiTypeRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND)
        );
        return apiTypeMapper.toApiTypeResponse(apiType);
    }
}
