package com.nguyen.master.NguyenMaster.ddd.repositoty.auth;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.auth.Users;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, BigInteger> {
    Optional<Users> findByEmail(String email);

    @Query("select u.email from Users as u where u.userId = :userId")
    String findEmailByUserId(@Param("userId") BigInteger userId);

    Users findUsersByUserId(BigInteger userId);
}
