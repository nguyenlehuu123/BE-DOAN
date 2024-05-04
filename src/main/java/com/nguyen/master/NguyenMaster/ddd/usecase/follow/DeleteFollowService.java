package com.nguyen.master.NguyenMaster.ddd.usecase.follow;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.AuditingEntityAction;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AccountRedis;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.repositoty.follow.FollowRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class DeleteFollowService extends BaseService {
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private AuditingEntityAction auditingEntityAction;

    @Autowired
    private StoryRepository storyRepository;

    public NormalDefaultResponse unfollow(BigInteger storyId) {
        AccountRedis accountRedis = auditingEntityAction.getUserInfo();
        StoryEntity storyEntity = storyRepository.findStoryEntitiesByStoryId(storyId);
        storyEntity.setFollowNumber(storyEntity.getFollowNumber() - 1);
        storyRepository.save(storyEntity);

        followRepository.deleteFollowEntitiesByUserIdAndStoryId(accountRedis.getUserId(), storyId);

        NormalDefaultResponse normalDefaultResponse = new NormalDefaultResponse();
        normalDefaultResponse.setMessage(getMessage(SystemMessageCode.SUCCESS_PROCESS));
        return normalDefaultResponse;
    }
}
