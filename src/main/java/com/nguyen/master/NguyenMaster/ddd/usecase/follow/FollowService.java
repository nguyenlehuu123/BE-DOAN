package com.nguyen.master.NguyenMaster.ddd.usecase.follow;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.AuditingEntityAction;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AccountRedis;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.auth.Users;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.follow.FollowEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.repositoty.auth.UserRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.follow.FollowRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService extends BaseService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private AuditingEntityAction auditingEntityAction;

    public NormalDefaultResponse followStory(BigInteger storyId) {
        AccountRedis accountRedis = auditingEntityAction.getUserInfo();
        checkStoryExitsInDb(storyId);
        checkUserIdExitsInDb(accountRedis.getUserId());
        checkUserFollowedStory(accountRedis.getUserId(), storyId);
        StoryEntity storyEntity = storyRepository.findStoryEntitiesByStoryId(storyId);
        storyEntity.setFollowNumber(storyEntity.getFollowNumber() + 1);
        storyRepository.save(storyEntity);
        followRepository.insertFollowEntity(accountRedis.getUserId(), storyId);

        NormalDefaultResponse normalDefaultResponse = new NormalDefaultResponse();
        normalDefaultResponse.setMessage(getMessage(SystemMessageCode.SUCCESS_PROCESS));
        return normalDefaultResponse;
    }

    private void checkStoryExitsInDb(BigInteger storyId) {
        StoryEntity storyEntity = storyRepository.findStoryEntitiesByStoryId(storyId);
        if (ObjectUtils.isEmpty(storyEntity)) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.STORY_EMPTY_NOT_RECORD));
            throw new Error400Exception(Constants.E404, errorMessages);
        }
    }

    private void checkUserIdExitsInDb(BigInteger userId) {
        Users users = userRepository.findUsersByUserId(userId);
        if (ObjectUtils.isEmpty(users)) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.USER_NOT_EXITS));
            throw new Error400Exception(Constants.E401, errorMessages);
        }
    }

    private void checkUserFollowedStory(BigInteger userId, BigInteger storyId) {
        FollowEntity followEntity = followRepository.findFollowEntitiesByUserIdAndStoryId(userId, storyId);
        if (!ObjectUtils.isEmpty(followEntity)) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.YOU_HAVE_FOLLOWED_THIS_STORY));
            throw new Error400Exception(Constants.E405, errorMessages);
        }
    }
}
