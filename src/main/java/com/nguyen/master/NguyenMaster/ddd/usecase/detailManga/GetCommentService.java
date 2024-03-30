package com.nguyen.master.NguyenMaster.ddd.usecase.detailManga;

import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.CommentEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.mangaDetail.CommentResponse;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.mangaDetail.GetCommentResponse;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetCommentService extends BaseService {
    @Autowired
    private CommentRepository commentRepository;

    public List<GetCommentResponse> getComment(BigInteger storyId) {
        List<CommentEntity> commentEntities = commentRepository.findCommentEntitiesByStoryId(storyId);
        List<GetCommentResponse> getCommentResponses = buildGetCommentResponse(commentEntities);
        return getCommentResponses;
    }

    private List<GetCommentResponse> buildGetCommentResponse(List<CommentEntity> commentEntities) {
        List<GetCommentResponse> getCommentResponses = new ArrayList<>();
        GetCommentResponse getCommentResponse = null;
        List<CommentResponse> commentResponses = null;
        for (CommentEntity commentEntity : commentEntities) {
            if (commentEntity.getSubCommentId() == null) {
                getCommentResponse = new GetCommentResponse();
                getCommentResponses.add(getCommentResponse);
                commentResponses = new ArrayList<>();
                getCommentResponse.setCommentId(commentEntity.getCommentId());
                getCommentResponse.setMessage(commentEntity.getContent());
                getCommentResponse.setTimeComment(commentEntity.getTimeComment());
                getCommentResponse.setLikeComment(commentEntity.getLikeComment());
                getCommentResponse.setDislikeComment(commentEntity.getDislikeComment());
                getCommentResponse.setEmailUserComment(commentEntity.getUsers().getEmail());
                getCommentResponse.setAvatar(commentEntity.getUsers().getAvatar());
                getCommentResponse.setCommentResponses(commentResponses);
            } else {
                CommentResponse commentResponse = new CommentResponse();
                commentResponse.setCommentId(commentEntity.getCommentId());
                commentResponse.setMessage(commentEntity.getContent());
                commentResponse.setTimeComment(commentEntity.getTimeComment());
                commentResponse.setLikeComment(commentEntity.getLikeComment());
                commentResponse.setDislikeComment(commentEntity.getDislikeComment());
                commentResponse.setEmailUserComment(commentEntity.getUsers().getEmail());
                commentResponse.setAvatar(commentEntity.getUsers().getAvatar());
                commentResponses.add(commentResponse);
            }
        }
        return getCommentResponses;
    }
}
