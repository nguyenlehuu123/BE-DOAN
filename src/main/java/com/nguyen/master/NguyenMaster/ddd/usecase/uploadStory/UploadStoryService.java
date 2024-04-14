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
import com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory.AuthorRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory.ChapterRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory.SearchStoryRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory.StoryGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadStoryService extends BaseService {
    @Autowired
    private AuditingEntityAction auditingEntityAction;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private SearchStoryRepository searchStoryRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private StoryGenreRepository storyGenreRepository;

    public NormalDefaultResponse insertStory(InsertStoryRequest request) {
        // build story
        StoryEntity storyEntity = StoryEntity.builder()
                .storyName(request.getStoryName())
                .description(request.getDescription())
                .image(request.getImage())
                .status(1) // TODO: UPDATED
                .releaseDate(DateUtil.convertStringToTimestamp(request.getReleaseDate()))
                .followNumber(BigInteger.valueOf(0))
                .likeNumber(BigInteger.valueOf(0))
                .ratingPoint(0)
                .build();
        auditingEntityAction.auditingInsertOrUpdate(storyEntity, true);

        // check story genre
        StoryGenreEntity storyGenreEntity = storyGenreRepository.findStoryGenreEntitiesByStoryGenreId(request.getStoryGenreId());
        if (ObjectUtils.isEmpty(storyGenreEntity)) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.STORY_GENRE_NOT_FOUND));
            throw new Error400Exception(Constants.E404, errorMessages);
        }
        storyEntity.setStoryGenreEntity(storyGenreEntity);
        StoryEntity storyEntitySave = searchStoryRepository.save(storyEntity);
        //build chapter
        for (ChapterAddRequest chapterAddRequest : request.getChaptersAdd()) {
            ChapterEntity chapterEntity = ChapterEntity.builder()
                    .chapterNumber(chapterAddRequest.getChapterNumber())
                    .statusKey(chapterAddRequest.getStatusKey())
                    .releaseDate(DateUtil.convertStringToTimestamp(chapterAddRequest.getReleaseDate()))
                    .urlFile(chapterAddRequest.getUrlFile())
                    .fileName(chapterAddRequest.getFileName())
                    .viewNumber(0)
                    .storyEntity(storyEntitySave)
                    .build();
            auditingEntityAction.auditingInsertOrUpdate(chapterEntity, true);
            chapterRepository.save(chapterEntity);
        }

        // check author
        List<AuthorEntity> authorEntities = new ArrayList<>();
        for (BigInteger authorId : request.getAuthorIds()) {
            AuthorEntity authorEntity = authorRepository.findAuthorEntityByAuthorId(authorId);
            if (ObjectUtils.isEmpty(authorEntity)) {
                List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.AUTHOR_NOT_FOUND));
                throw new Error400Exception(Constants.E405, errorMessages);
            }
            authorEntities.add(authorEntity);
        }
        storyEntitySave.setAuthorEntities(authorEntities);
        searchStoryRepository.save(storyEntitySave);

        NormalDefaultResponse normalDefaultResponse = new NormalDefaultResponse();
        normalDefaultResponse.setMessage(SystemMessageCode.SUCCESS_PROCESS);
        return normalDefaultResponse;
    }
}
