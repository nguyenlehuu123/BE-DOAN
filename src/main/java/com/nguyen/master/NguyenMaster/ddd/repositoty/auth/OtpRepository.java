package com.nguyen.master.NguyenMaster.ddd.repositoty.auth;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.auth.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

public interface OtpRepository extends JpaRepository<Otp, BigInteger> {
    Otp findByEmail(String email);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void delete(Otp otp);

    void deleteByEmail(String email);
}
