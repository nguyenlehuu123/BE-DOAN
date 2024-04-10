package com.nguyen.master.NguyenMaster.ddd.presentation.uploadStory;

import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.uploadStory.InsertStoryRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.uploadStory.SearchStoryRequest;
import com.nguyen.master.NguyenMaster.ddd.usecase.uploadStory.UploadStoryService;
import com.nguyen.master.NguyenMaster.ddd.usecase.uploadStory.SearchStoryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/upload-story")
public class UploadStoryController {
    @Autowired
    private SearchStoryService searchStoryService;

    @Autowired
    private UploadStoryService uploadStoryService;
    @GetMapping("/search")
    public ResponseEntity<?> getCommentManga(@Parameter(name = "searchStoryRequest", required = true, in = ParameterIn.QUERY) SearchStoryRequest searchStoryRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(searchStoryService.search(searchStoryRequest));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadStory(@RequestBody InsertStoryRequest insertStoryRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(uploadStoryService.insertStory(insertStoryRequest));
    }
}
