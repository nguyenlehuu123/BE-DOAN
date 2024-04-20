package com.nguyen.master.NguyenMaster.ddd.usecase.detailManga;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.AuditingEntityAction;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AccountRedis;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.RatingEntity;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.RatingRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class RatingMangaService extends BaseService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private AuditingEntityAction auditingEntityAction;

    public NormalDefaultResponse ratingManga(BigInteger storyId, Integer rating) {

        if (storyId == null || rating == null) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.NO_CONDITIONS_WERE_POSTER));
            throw new Error400Exception(Constants.E401, errorMessages);
        }

        AccountRedis accountRedis = auditingEntityAction.getUserInfo();
        BigInteger userId = accountRedis.getUserId();

        RatingEntity ratingEntity = RatingEntity.builder()
                .storyId(storyId)
                .userId(userId)
                .rating(rating)
                .build();

        ratingRepository.save(ratingEntity);

        // Update rating point
        StoryEntity storyEntity = storyRepository.findStoryEntitiesByStoryId(storyId);
        List<RatingEntity> ratingEntities = ratingRepository.findRatingEntitiesByStoryId(storyId);
        Integer totalRating = ratingEntities.stream().map(RatingEntity::getRating).reduce(0, Integer::sum);
        storyEntity.setRatingPoint(totalRating / ratingEntities.size());
        storyRepository.save(storyEntity);

        return NormalDefaultResponse.builder()
                .message(SystemMessageCode.SUCCESS_PROCESS)
                .build();
    }

    public NormalDefaultResponse updateRatingManga(BigInteger storyId, Integer rating) {
        if (storyId == null || rating == null) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.NO_CONDITIONS_WERE_POSTER));
            throw new Error400Exception(Constants.E401, errorMessages);
        }

        AccountRedis accountRedis = auditingEntityAction.getUserInfo();
        BigInteger userId = accountRedis.getUserId();

        RatingEntity ratingEntity = ratingRepository.findRatingEntityByStoryIdAndUserId(storyId, userId);
        ratingEntity.setRating(rating);
        ratingRepository.save(ratingEntity);

        // Update rating point
        StoryEntity storyEntity = storyRepository.findStoryEntitiesByStoryId(storyId);
        List<RatingEntity> ratingEntities = ratingRepository.findRatingEntitiesByStoryId(storyId);
        Integer totalRating = ratingEntities.stream().map(RatingEntity::getRating).reduce(0, Integer::sum);
        storyEntity.setRatingPoint(totalRating / ratingEntities.size());
        storyRepository.save(storyEntity);

        return NormalDefaultResponse.builder()
                .message(SystemMessageCode.SUCCESS_PROCESS)
                .build();
    }
}
