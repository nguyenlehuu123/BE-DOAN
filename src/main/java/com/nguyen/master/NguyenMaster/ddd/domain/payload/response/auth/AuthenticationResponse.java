package com.nguyen.master.NguyenMaster.ddd.domain.payload.response.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String accessToken;

    private String refreshToken;

    private String email;

    private Enum role;

    private String avatar;

    private Date expireTime;
}
