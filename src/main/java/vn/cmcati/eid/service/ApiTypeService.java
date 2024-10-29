package vn.cmcati.eid.service;

import org.springframework.stereotype.Service;
import vn.cmcati.eid.dto.request.ApiTypeRequest;
import vn.cmcati.eid.dto.response.ApiTypeResponse;


import java.util.List;

@Service
public interface ApiTypeService {
    List<ApiTypeResponse> getApiTypes();
    ApiTypeResponse addApiType(ApiTypeRequest apiTypeRequest);
    ApiTypeResponse updateApiType(Long id, ApiTypeRequest apiTypeRequest);
    boolean deleteApiType(Long id);
    ApiTypeResponse getApiType(Long id);

}
