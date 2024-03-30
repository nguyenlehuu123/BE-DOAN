package com.nguyen.master.NguyenMaster.ddd.presentation.mangaDetail;

import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.mangaDetail.CommentRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.mangaDetail.CommentResponse;
import com.nguyen.master.NguyenMaster.ddd.usecase.detailManga.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.math.BigInteger;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/story/{storyId}/comment")
    public void processComment(@DestinationVariable BigInteger storyId, @Payload CommentRequest commentRequest, Principal principal) {
        CommentResponse commentResponse = commentService.saveComment(commentRequest, storyId);
        simpMessagingTemplate.convertAndSend("/topic/story/" + storyId + "/comment", commentResponse);
    }
}
