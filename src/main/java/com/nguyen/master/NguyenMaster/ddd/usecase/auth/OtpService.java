package com.nguyen.master.NguyenMaster.ddd.usecase.auth;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.util.AppUtils;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.auth.Otp;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.auth.OtpRequest;
import com.nguyen.master.NguyenMaster.ddd.dto.auth.EmailDetails;
import com.nguyen.master.NguyenMaster.ddd.repositoty.auth.OtpRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
@Slf4j
public class OtpService extends BaseService {
    private final Integer EXPIRES = 5 * 60000;
    private final OtpRepository otpRepository;
    private final EmailService emailService;

    public NormalDefaultResponse sendOtp(OtpRequest otpRequest) {
        Otp otpOld = otpRepository.findByEmail(otpRequest.getEmail());
        if (!ObjectUtils.isEmpty(otpOld)) {
            otpRepository.delete(otpOld);
        }
        String otp = AppUtils.generateOtp();
        otpRepository.save(Otp.builder()
                .email(otpRequest.getEmail())
                .otp(otp)
                .expiresAt(new Timestamp(System.currentTimeMillis() + EXPIRES))
                .build());
        emailService.sendEmail(EmailDetails.builder()
                .subject("DO NOT DISCLOSE!!!")
                .recipient(otpRequest.getEmail())
                .messageBody("This organization has send you an otp. This otp expires in 5 minutes. OTP: " + otp)
                .build());

        NormalDefaultResponse normalDefaultResponse = new NormalDefaultResponse();
        normalDefaultResponse.setMessage(getMessage(SystemMessageCode.EMAIL_SENT_SUCCESS));
        return normalDefaultResponse;
    }
}
