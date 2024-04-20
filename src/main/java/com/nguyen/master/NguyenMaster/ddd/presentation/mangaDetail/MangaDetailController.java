package com.nguyen.master.NguyenMaster.ddd.presentation.mangaDetail;

import com.nguyen.master.NguyenMaster.ddd.usecase.detailManga.GetCommentService;
import com.nguyen.master.NguyenMaster.ddd.usecase.detailManga.GetDetailStoryService;
import com.nguyen.master.NguyenMaster.ddd.usecase.detailManga.LikesMangaService;
import com.nguyen.master.NguyenMaster.ddd.usecase.detailManga.RatingMangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/v1/manga-detail")
public class MangaDetailController {
    @Autowired
    private GetDetailStoryService getDetailStoryService;

    @Autowired
    private GetCommentService getCommentService;

    @Autowired
    private LikesMangaService likesMangaService;

    @Autowired
    private RatingMangaService ratingMangaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMangaDetail(@PathVariable BigInteger id) {
        return ResponseEntity.status(HttpStatus.OK).body(getDetailStoryService.getDetailStory(id));
    }

    @GetMapping("/comments/{storyId}")
    public ResponseEntity<?> getCommentManga(@PathVariable BigInteger storyId) {
        return ResponseEntity.status(HttpStatus.OK).body(getCommentService.getComment(storyId));
    }

    @PutMapping("/manga-detail/likes/{storyId}")
    public ResponseEntity<?> likeManga(@PathVariable BigInteger storyId) {
        return ResponseEntity.status(HttpStatus.OK).body(likesMangaService.likeManga(storyId));
    }

    @DeleteMapping("/manga-detail/likes/{storyId}")
    public ResponseEntity<?> unlikeManga(@PathVariable BigInteger storyId) {
        return ResponseEntity.status(HttpStatus.OK).body(likesMangaService.unlikeManga(storyId));
    }

    @PutMapping("/manga-detail/rating/{storyId}")
    public ResponseEntity<?> ratingManga(@PathVariable BigInteger storyId, @RequestBody Integer rating) {
        return ResponseEntity.status(HttpStatus.OK).body(ratingMangaService.ratingManga(storyId, rating));
    }
}
