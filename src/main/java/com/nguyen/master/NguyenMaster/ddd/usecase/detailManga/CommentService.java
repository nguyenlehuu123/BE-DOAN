package com.nguyen.master.NguyenMaster.ddd.usecase.detailManga;

import com.nguyen.master.NguyenMaster.core.common.AuditingEntityAction;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.core.util.JwtUtils;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.auth.Users;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.CommentEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.mangaDetail.CommentRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.mangaDetail.CommentResponse;
import com.nguyen.master.NguyenMaster.ddd.repositoty.auth.UserRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.CommentRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.StoryRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentService extends BaseService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuditingEntityAction auditingEntityAction;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public CommentResponse saveComment(CommentRequest commentRequest, BigInteger storyId) {

        if (StringUtils.isEmpty(commentRequest.getToken())) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.USER_NEEDS_TO_LOGIN));
            throw new Error400Exception(Constants.E401, errorMessages);
        }

        CommentEntity commentEntity = buildCommentEntity(commentRequest, storyId);
        CommentEntity comment = commentRepository.save(commentEntity);
        CommentResponse commentResponse = buildCommentResponse(comment);
        return commentResponse;
    }

    private CommentEntity buildCommentEntity(CommentRequest commentRequest, BigInteger storyId) {
        String token = StringUtils.isNotEmpty(commentRequest.getToken()) && commentRequest.getToken().startsWith("Bearer ") ? commentRequest.getToken().substring(7) : null;
        CommentEntity commentEntity = new CommentEntity();
        if (StringUtils.isNotEmpty(token) && jwtUtils.isValidateToken(token)) {
            String userId = jwtUtils.getUserIdFromJwt(token);
            Users users = userRepository.findUsersByUserId(new BigInteger(userId));
            StoryEntity storyEntity = storyRepository.findStoryEntitiesByStoryId(storyId);
            commentEntity = CommentEntity.builder()
                    .users(users)
                    .storyEntity(storyEntity)
                    .content(commentRequest.getMessage())
                    .timeComment(new Timestamp(System.currentTimeMillis()))
                    .likeComment(0)
                    .dislikeComment(0)
                    .subCommentId(commentRequest.getCommentId())
                    .build();
        }
        return commentEntity;
    }

    private CommentResponse buildCommentResponse(CommentEntity commentEntity) {
        return CommentResponse.builder()
                .commentId(commentEntity.getCommentId())
                .message(commentEntity.getContent())
                .timeComment(commentEntity.getTimeComment())
                .likeComment(commentEntity.getLikeComment())
                .dislikeComment(commentEntity.getDislikeComment())
                .emailUserComment(commentEntity.getUsers().getEmail())
                .avatar(commentEntity.getUsers().getAvatar())
                .subCommentId(commentEntity.getSubCommentId())
                .build();
    }
}