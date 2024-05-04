package com.nguyen.master.NguyenMaster.ddd.repositoty.auth;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.auth.Users;
import com.nguyen.master.NguyenMaster.ddd.dto.userManagement.UserManagementDTO;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT new com.nguyen.master.NguyenMaster.ddd.dto.userManagement.UserManagementDTO(" +
            "u.userId, " +
            "u.email, " +
            "u.role) " +
            "FROM Users as u ")
    Page<UserManagementDTO> findUserManagementPaging(Pageable pageable);

    void deleteUserByUserId(BigInteger userId);

    Users findUserByUserId(BigInteger userId);
}
