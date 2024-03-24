package com.nguyen.master.NguyenMaster.ddd.presentation.auth;

import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.auth.OtpRequest;
import com.nguyen.master.NguyenMaster.ddd.usecase.auth.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/otp/")
public class OtpController {
    @Autowired
    private OtpService otpService;

    @PostMapping("send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody OtpRequest otpRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(otpService.sendOtp(otpRequest));
    }
}
