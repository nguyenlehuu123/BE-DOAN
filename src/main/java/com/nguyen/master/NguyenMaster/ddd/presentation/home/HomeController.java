package com.nguyen.master.NguyenMaster.ddd.presentation.home;

import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.PagingRequest;
import com.nguyen.master.NguyenMaster.ddd.usecase.home.GetStoryHotService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private GetStoryHotService getStoryHotService;


    @GetMapping("/option-header")
    public ResponseEntity<?> getOptionHeader() {
        return ResponseEntity.status(HttpStatus.OK).body(getStoryHotService.getAllOptionHeader());
    }

    @GetMapping("/story-hot")
    public ResponseEntity<?> getStoryHot() {
        return ResponseEntity.status(HttpStatus.OK).body(getStoryHotService.getStoryHot());
    }

    @GetMapping("/story-new-update")
    public ResponseEntity<?> getStoryUpdate(@Parameter(name = "pagingRequest", required = true, in = ParameterIn.QUERY) PagingRequest pagingRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(getStoryHotService.getStoryUpdateNew(pagingRequest));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAllStory() {
        return ResponseEntity.status(HttpStatus.OK).body(getStoryHotService.searchAllStory());
    }

    @GetMapping("/get-story-genre/{storyGenreId}")
    public ResponseEntity<?> getStoryGenre(@PathVariable Integer storyGenreId, @Parameter(name = "pagingRequest", required = true, in = ParameterIn.QUERY) PagingRequest pagingRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(getStoryHotService.getStoryGenre(storyGenreId, pagingRequest));
    }
}