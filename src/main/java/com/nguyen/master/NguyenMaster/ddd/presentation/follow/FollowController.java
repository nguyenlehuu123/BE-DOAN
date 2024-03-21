package com.nguyen.master.NguyenMaster.ddd.presentation.follow;

import com.nguyen.master.NguyenMaster.ddd.usecase.follow.DeleteFollowService;
import com.nguyen.master.NguyenMaster.ddd.usecase.follow.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/v1/follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    @Autowired
    private DeleteFollowService deleteFollowService;

    @PostMapping("/{storyId}")
    public ResponseEntity<?> followStory(@PathVariable BigInteger storyId) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.followStory(storyId));
    }

    @DeleteMapping("/{storyId}")
    public ResponseEntity<?> unfollowStory(@PathVariable BigInteger storyId) {
        return ResponseEntity.status(HttpStatus.OK).body(deleteFollowService.unfollow(storyId));
    }

}
