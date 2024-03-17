package com.nguyen.master.NguyenMaster.ddd.repositoty.auth;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.auth.Tokens;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface TokenRepository extends JpaRepository<Tokens, BigInteger> {

    @Query("select t.refreshToken from Tokens as t " +
            "inner join t.users as u on t.id = u.userId " +
            "where u.userId = :userId and t.revoked = false and t.expired = false")
    String findByUserIdEAndExpi(@Param("userId") BigInteger userId);
}
