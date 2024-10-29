package vn.cmcati.eid.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import vn.cmcati.eid.service.TokenService;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenController {
    TokenService tokenService;

//    @GetMapping("/{userId}")
//    ApiResponse<List<TokenResponse>> getTokenByUserId(@PathVariable Long userId) {
//        return ApiResponse.<TokenResponse>builder()
//                .result(tokenService.getTokenByUserId(userId))
//                .build();
//    }
}
