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
import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.RatingId;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.mangaDetail.RatingOverviewResponse;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.RatingRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class RatingMangaService extends BaseService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private AuditingEntityAction auditingEntityAction;

    public NormalDefaultResponse ratingManga(BigInteger storyId, Float rating) {

        if (storyId == null || rating == null) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.NO_CONDITIONS_WERE_POSTER));
            throw new Error400Exception(Constants.E401, errorMessages);
        }

        AccountRedis accountRedis = auditingEntityAction.getUserInfo();
        BigInteger userId = accountRedis.getUserId();

        // Always create a new RatingEntity
        RatingEntity ratingEntity = RatingEntity.builder()
                .id(new RatingId(storyId, userId))
                .rating(rating)
                .build();
        ratingRepository.save(ratingEntity);

        // Update rating point
        StoryEntity storyEntity = storyRepository.findStoryEntitiesByStoryId(storyId);
        List<RatingEntity> ratingEntities = ratingRepository.findRatingEntitiesByIdStoryId(storyId);
        Float totalRating = ratingEntities.stream().map(RatingEntity::getRating).reduce((float) 0, Float::sum);
        storyEntity.setRatingPoint(totalRating / ratingEntities.size());
        storyRepository.save(storyEntity);

        return NormalDefaultResponse.builder()
                .message(SystemMessageCode.SUCCESS_PROCESS)
                .build();
    }

    public RatingOverviewResponse ratingOverview(BigInteger storyId) {
        List<RatingEntity> ratingEntities = ratingRepository.findRatingEntitiesByIdStoryId(storyId);
        Float totalRating = ratingEntities.stream().map(RatingEntity::getRating).reduce((float) 0, Float::sum);
        float averageRating = 0.0f;
        if (!ratingEntities.isEmpty()) {
            averageRating = totalRating / ratingEntities.size();
        }
        int totalRatingCount = ratingEntities.size();
        int total1Star = (int) ratingEntities.stream().filter(ratingEntity -> ratingEntity.getRating() <= 1 && ratingEntity.getRating() >= 0).count();
        int total2Star = (int) ratingEntities.stream().filter(ratingEntity -> ratingEntity.getRating() <= 2 && ratingEntity.getRating() > 1).count();
        int total3Star = (int) ratingEntities.stream().filter(ratingEntity -> ratingEntity.getRating() <= 3 && ratingEntity.getRating() > 2).count();
        int total4Star = (int) ratingEntities.stream().filter(ratingEntity -> ratingEntity.getRating() <= 4 && ratingEntity.getRating() > 3).count();
        int total5Star = (int) ratingEntities.stream().filter(ratingEntity -> ratingEntity.getRating() <= 5 && ratingEntity.getRating() > 4).count();

        Integer percent1Star = total1Star == 0 ? 0 : total1Star * 100 / totalRatingCount;
        Integer percent2Star = total2Star == 0 ? 0 : total2Star * 100 / totalRatingCount;
        Integer percent3Star = total3Star == 0 ? 0 : total3Star * 100 / totalRatingCount;
        Integer percent4Star = total4Star == 0 ? 0 : total4Star * 100 / totalRatingCount;
        Integer percent5Star = total5Star == 0 ? 0 : total5Star * 100 / totalRatingCount;

        return RatingOverviewResponse.builder()
                .averageRating(averageRating)
                .averageRating(averageRating)
                .totalRating(totalRatingCount)
                .total5Star(total5Star)
                .total4Star(total4Star)
                .total3Star(total3Star)
                .total2Star(total2Star)
                .total1Star(total1Star)
                .percent5Star(percent5Star)
                .percent4Star(percent4Star)
                .percent3Star(percent3Star)
                .percent2Star(percent2Star)
                .percent1Star(percent1Star)
                .build();
    }
    public Float isRating(BigInteger storyId) {
        AccountRedis accountRedis = auditingEntityAction.getUserInfo();
        BigInteger userId = accountRedis.getUserId();
        Optional<RatingEntity> ratingEntity = ratingRepository.findById(RatingId.builder().storyId(storyId).userId(userId).build());
        return ratingEntity.map(RatingEntity::getRating).orElse((float) 0);
    }
}
