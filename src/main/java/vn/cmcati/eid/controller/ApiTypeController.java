package vn.cmcati.eid.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.cmcati.eid.dto.request.ApiTypeRequest;
import vn.cmcati.eid.dto.response.ApiResponse;
import vn.cmcati.eid.dto.response.ApiTypeResponse;
import vn.cmcati.eid.service.ApiTypeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/apitype")
public class ApiTypeController {
    ApiTypeService apiTypeService;

    @GetMapping("")
    public ApiResponse<List<ApiTypeResponse>> getApiTypes() {
        return ApiResponse.<List<ApiTypeResponse>>builder()
                .result(apiTypeService.getApiTypes())
                .build();

    }
    @GetMapping("/{apiTypeId}")
    public ApiResponse<ApiTypeResponse> getApiTypeById(@PathVariable(value = "apiTypeId") Long id) {
        return ApiResponse.<ApiTypeResponse>builder()
                .result(apiTypeService.getApiType(id))
                .build();

    }

    @PostMapping("")
    public ApiResponse<ApiTypeResponse> createApiType(@RequestBody ApiTypeRequest apiTypeRequest) {
        return ApiResponse.<ApiTypeResponse>builder()
                .result(apiTypeService.addApiType(apiTypeRequest))
                .build();
    }

    @PutMapping("/{apiTypeId}")
    public ApiResponse<ApiTypeResponse> updateApiType(@PathVariable(value = "apiTypeId") Long id, @RequestBody ApiTypeRequest apiTypeRequest){
        return ApiResponse.<ApiTypeResponse>builder()
                .result(apiTypeService.updateApiType(id, apiTypeRequest))
                .build();
    }

    @DeleteMapping("/{apiTypeId}")
    public ApiResponse<Boolean> deleteApiType(@PathVariable(value = "apiTypeId") Long id) {
        return ApiResponse.<Boolean>builder()
                .result(apiTypeService.deleteApiType(id))
                .build();
    }


}
