package com.nguyen.master.NguyenMaster.ddd.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRedis extends BaseEntity {
    private BigInteger userId;
    private String accessToken;
}
