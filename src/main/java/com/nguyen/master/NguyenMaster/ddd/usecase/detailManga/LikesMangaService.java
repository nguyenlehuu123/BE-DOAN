package com.nguyen.master.NguyenMaster.ddd.usecase.detailManga;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.AuditingEntityAction;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AccountRedis;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.LikesEntity;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.LikesRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class LikesMangaService extends BaseService {
    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private AuditingEntityAction auditingEntityAction;

    public NormalDefaultResponse likeManga(BigInteger storyId) {
        AccountRedis accountRedis = auditingEntityAction.getUserInfo();
        BigInteger userId = accountRedis.getUserId();

        LikesEntity likesEntity = LikesEntity.builder()
                .storyId(storyId)
                .userId(userId)
                .build();
        likesRepository.save(likesEntity);

        // Update like number
        StoryEntity storyEntity = storyRepository.findStoryEntitiesByStoryId(storyId);
        storyEntity.setLikeNumber(storyEntity.getLikeNumber() + 1);

        storyRepository.save(storyEntity);

        return NormalDefaultResponse.builder()
                .message(SystemMessageCode.SUCCESS_PROCESS)
                .build();
    }

    public NormalDefaultResponse unlikeManga(BigInteger storyId) {
        AccountRedis accountRedis = auditingEntityAction.getUserInfo();
        BigInteger userId = accountRedis.getUserId();

        LikesEntity likesEntity = likesRepository.findLikesEntityByStoryIdAndUserId(storyId, userId);
        likesRepository.delete(likesEntity);

        // Update like number
        StoryEntity storyEntity = storyRepository.findStoryEntitiesByStoryId(storyId);
        storyEntity.setLikeNumber(storyEntity.getLikeNumber() - 1);

        storyRepository.save(storyEntity);

        return NormalDefaultResponse.builder()
                .message(SystemMessageCode.SUCCESS_PROCESS)
                .build();
    }
}
