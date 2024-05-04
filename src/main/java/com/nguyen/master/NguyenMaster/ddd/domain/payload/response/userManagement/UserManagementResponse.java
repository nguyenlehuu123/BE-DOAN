package com.nguyen.master.NguyenMaster.ddd.domain.payload.response.userManagement;

import com.nguyen.master.NguyenMaster.ddd.dto.userManagement.UserManagementDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserManagementResponse {
    private Integer total;
    private List<UserManagementDTO> userManagementDTOS;
}
