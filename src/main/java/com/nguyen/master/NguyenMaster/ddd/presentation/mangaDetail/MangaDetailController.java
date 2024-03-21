package com.nguyen.master.NguyenMaster.ddd.presentation.mangaDetail;

import com.nguyen.master.NguyenMaster.ddd.usecase.detailManga.GetDetailStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/v1/manga-detail")
public class MangaDetailController {
    @Autowired
    private GetDetailStoryService getDetailStoryService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getMangaDetail(@PathVariable BigInteger id) {
        return ResponseEntity.status(HttpStatus.OK).body(getDetailStoryService.getDetailStory(id));
    }
}
