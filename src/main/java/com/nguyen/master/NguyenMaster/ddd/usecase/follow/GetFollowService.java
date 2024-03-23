package com.nguyen.master.NguyenMaster.ddd.usecase.follow;

import com.nguyen.master.NguyenMaster.core.common.AuditingEntityAction;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AccountRedis;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.follow.FollowEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.repositoty.follow.FollowRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@Service
public class GetFollowService extends BaseService {
    @Autowired
    private AuditingEntityAction auditingEntityAction;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private StoryRepository storyRepository;

    public List<StoryEntity> getFollow() {
        AccountRedis accountRedis = auditingEntityAction.getUserInfo();
        Collection<FollowEntity> followEntities = followRepository.findFollowEntitiesByUserId(accountRedis.getUserId());
        if (CollectionUtils.isEmpty(followEntities)) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.YOU_HAVE_NOT_FOLLOWED_ANY_STORY));
            throw new Error400Exception(Constants.E404, errorMessages);
        }
        List<BigInteger> storyIds = followEntities.stream().map(FollowEntity::getStoryId).distinct().toList();
        List<StoryEntity> storyEntities = storyRepository.findStoryEntitiesByStoryIds(storyIds);
        return storyEntities;
    }
}
