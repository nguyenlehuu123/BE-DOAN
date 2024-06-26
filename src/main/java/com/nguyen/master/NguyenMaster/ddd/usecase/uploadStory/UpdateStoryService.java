package com.nguyen.master.NguyenMaster.ddd.usecase.uploadStory;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.AuditingEntityAction;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.core.util.DateUtil;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AuthorEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryGenreEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.ChapterEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.uploadStory.ChapterAddRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.uploadStory.InsertStoryRequest;
import com.nguyen.master.NguyenMaster.ddd.repositoty.follow.FollowRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.CommentRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.LikesRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.RatingRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.StoryRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.readHistory.ReadHistoryRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory.AuthorRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory.ChapterRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory.StoryGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateStoryService extends BaseService {
    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private AuditingEntityAction auditingEntityAction;

    @Autowired
    private StoryGenreRepository storyGenreRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ReadHistoryRepository readHistoryRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private CommentRepository commentRepository;

    public NormalDefaultResponse updateStory(InsertStoryRequest request, BigInteger storyId) {

        StoryEntity storyEntity = storyRepository.findStoryEntitiesByStoryId(storyId);
        if (ObjectUtils.isEmpty(storyEntity)) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.STORY_EMPTY_NOT_RECORD));
            throw new Error400Exception(Constants.E404, errorMessages);
        }

        storyEntity.setStoryName(request.getStoryName());
        storyEntity.setDescription(request.getDescription());
        storyEntity.setReleaseDate(DateUtil.convertStringToTimestamp(request.getReleaseDate()));
        storyEntity.setImage(request.getImage());

        // check story genre
        StoryGenreEntity storyGenreEntity = storyGenreRepository.findStoryGenreEntitiesByStoryGenreId(request.getStoryGenreId());
        if (ObjectUtils.isEmpty(storyGenreEntity)) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.STORY_GENRE_NOT_FOUND));
            throw new Error400Exception(Constants.E405, errorMessages);
        }
        storyEntity.setStoryGenreEntity(storyGenreEntity);

        // check author
        List<AuthorEntity> authorEntities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getAuthorIds())) {
            for (BigInteger authorId : request.getAuthorIds()) {
                AuthorEntity authorEntity = authorRepository.findAuthorEntityByAuthorId(authorId);
                if (ObjectUtils.isEmpty(authorEntity)) {
                    List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.AUTHOR_NOT_FOUND));
                    throw new Error400Exception(Constants.E406, errorMessages);
                }
                authorEntities.add(authorEntity);
            }
        }
        storyEntity.setAuthorEntities(authorEntities);

        List<ChapterEntity> chapterEntities = buildChapterEntities(request.getChaptersAdd(), storyEntity);
        storyEntity.setChapterEntities(chapterEntities);
        storyRepository.save(storyEntity);

        NormalDefaultResponse normalDefaultResponse = new NormalDefaultResponse();
        normalDefaultResponse.setMessage(SystemMessageCode.SUCCESS_PROCESS);
        return normalDefaultResponse;
    }


    private List<ChapterEntity> buildChapterEntities(List<ChapterAddRequest> insertStoryRequests, StoryEntity storyEntity) {
        chapterRepository.deleteChapterEntitiesByStoryEntity(storyEntity);
        List<ChapterEntity> chapterEntities = new ArrayList<>();
        for (ChapterAddRequest chapterAddRequest : insertStoryRequests) {
            ChapterEntity chapterEntity = ChapterEntity.builder()
                    .chapterNumber(chapterAddRequest.getChapterNumber())
                    .statusKey(chapterAddRequest.getStatusKey())
                    .releaseDate(DateUtil.convertStringToTimestamp(chapterAddRequest.getReleaseDate()))
                    .urlFile(chapterAddRequest.getUrlFile())
                    .fileName(chapterAddRequest.getFileName())
                    .viewNumber(0)
                    .storyEntity(storyEntity)
                    .build();
            auditingEntityAction.auditingInsertOrUpdate(chapterEntity, true);
            chapterEntities.add(chapterEntity);
        }
        return chapterEntities;
    }

    public Integer getSequenceNumberStory() {
        return storyRepository.getLastAutoIncrementValue() + 1;
    }

    public NormalDefaultResponse deleteStory(BigInteger storyId) {
        StoryEntity storyEntity = storyRepository.findStoryEntitiesByStoryId(storyId);
        if (storyEntity == null) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.STORY_EMPTY_NOT_RECORD));
            throw new Error400Exception(Constants.E404, errorMessages);
        }
        likesRepository.deleteLikesEntitiesByStoryId(storyId);
        ratingRepository.deleteRatingEntitiesByIdStoryId(storyId);
        readHistoryRepository.deleteReadHistoryEntitiesByIdStoryId(storyId);
        followRepository.deleteFollowEntitiesByStoryId(storyId);
        chapterRepository.deleteChapterEntitiesByStoryEntity(storyEntity);
        commentRepository.deleteCommentEntitiesByStoryEntity_StoryId(storyId);

        storyRepository.deleteStoryEntityByStoryId(storyId);

        NormalDefaultResponse normalDefaultResponse = new NormalDefaultResponse();
        normalDefaultResponse.setMessage(SystemMessageCode.SUCCESS_PROCESS);
        return normalDefaultResponse;
    }
}
