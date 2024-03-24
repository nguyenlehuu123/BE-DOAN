package com.nguyen.master.NguyenMaster.ddd.presentation.auth;

import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.RegisterUserRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.auth.AuthenticationResponse;
import com.nguyen.master.NguyenMaster.ddd.usecase.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> regisUer(@RequestBody RegisterUserRequest registerUserRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.registerUser(registerUserRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody RegisterUserRequest registerUserRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authenticate(registerUserRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken() {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.refreshToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.logout());
    }
}
