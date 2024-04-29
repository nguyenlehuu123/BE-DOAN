package com.nguyen.master.NguyenMaster.ddd.presentation.readHistory;

import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.PagingRequest;
import com.nguyen.master.NguyenMaster.ddd.usecase.readHistory.ReadHistoryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/v1/history")
public class ReadHistoryController {

    @Autowired
    private ReadHistoryService readHistoryService;

    @GetMapping("")
    public ResponseEntity<?> getReadHistory(@Parameter(name = "pagingRequest", required = true, in = ParameterIn.QUERY) PagingRequest pagingRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(readHistoryService.getReadHistory(pagingRequest));
    }

    @PostMapping("/{storyId}")
    public ResponseEntity<?> addReadHistory(@PathVariable BigInteger storyId) {
        return ResponseEntity.status(HttpStatus.OK).body(readHistoryService.addReadHistory(storyId));
    }
}
