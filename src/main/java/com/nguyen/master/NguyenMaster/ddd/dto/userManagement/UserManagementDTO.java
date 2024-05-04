package com.nguyen.master.NguyenMaster.ddd.dto.userManagement;

import com.nguyen.master.NguyenMaster.core.constant.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserManagementDTO {
    private BigInteger userId;
    private String email;
    private Role role;
}
