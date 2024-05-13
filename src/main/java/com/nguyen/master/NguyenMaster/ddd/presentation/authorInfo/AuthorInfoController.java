package com.nguyen.master.NguyenMaster.ddd.presentation.authorInfo;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.AuthorEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.authorInfo.AuthorInfoRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.EmailRequest;
import com.nguyen.master.NguyenMaster.ddd.usecase.authorInfo.AuthorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/author-info")
public class AuthorInfoController {
    @Autowired
    private AuthorInfoService authorInfoService;
    @PostMapping("/get")
    public ResponseEntity<AuthorEntity> getAuthorInfo(@RequestBody EmailRequest email) {
        return ResponseEntity.status(HttpStatus.OK).body(authorInfoService.getAuthorInfo(email));
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveAuthorInfo(@RequestBody AuthorInfoRequest authorInfoRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authorInfoService.saveAuthorInfo(authorInfoRequest));
    }
}
