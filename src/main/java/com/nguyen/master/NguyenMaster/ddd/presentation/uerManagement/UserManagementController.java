package com.nguyen.master.NguyenMaster.ddd.presentation.uerManagement;

import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.PagingRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.userManagement.UpdateRoleRequest;
import com.nguyen.master.NguyenMaster.ddd.usecase.userManagement.GetUserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/v1/user-management")
public class UserManagementController {
    @Autowired
    private GetUserService getUserService;
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@Parameter(name = "pagingRequest", required = true, in = ParameterIn.QUERY) PagingRequest pagingRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(getUserService.getUser(pagingRequest));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable BigInteger userId) {
        return ResponseEntity.status(HttpStatus.OK).body(getUserService.deleteUser(userId));
    }

    @PutMapping("/update-role/{userId}")
    public ResponseEntity<?> updateRoleUser(@PathVariable BigInteger userId, @RequestBody UpdateRoleRequest updateRoleRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(getUserService.updateRoleUser(userId, updateRoleRequest));
    }
}
