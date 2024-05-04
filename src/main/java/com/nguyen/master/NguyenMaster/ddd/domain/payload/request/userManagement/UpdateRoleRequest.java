package com.nguyen.master.NguyenMaster.ddd.domain.payload.request.userManagement;

import com.nguyen.master.NguyenMaster.core.constant.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRoleRequest {
    private Role role;
}
