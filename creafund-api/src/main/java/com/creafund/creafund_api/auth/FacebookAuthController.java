package com.creafund.creafund_api.auth;


import com.creafund.creafund_api.Dto.FacebookLoginRequest;
import com.creafund.creafund_api.Dto.JwtResponse;
import com.creafund.creafund_api.services.FacebookAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class FacebookAuthController {

    private final FacebookAuthService facebookAuthService;

    public FacebookAuthController(FacebookAuthService facebookAuthService) {
        this.facebookAuthService = facebookAuthService;
    }

    @PostMapping("/facebook")
    public ResponseEntity<JwtResponse> loginFacebook(@RequestBody FacebookLoginRequest request) {
        JwtResponse jwt = facebookAuthService.loginWithFacebook(request.getAccessToken());
        return ResponseEntity.ok(jwt);
    }
}
