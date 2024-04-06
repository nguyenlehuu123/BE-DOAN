package com.nguyen.master.NguyenMaster.ddd.usecase.detailManga;

import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.ChapterEntity;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;

@Service
public class GetDetailStoryService extends BaseService {

    @Autowired
    private StoryRepository storyRepository;

    public StoryEntity getDetailStory(BigInteger storyId) {

        StoryEntity storyEntity = storyRepository.findStoryEntitiesByStoryId(storyId);
        storyEntity.getChapterEntities().sort(Comparator.comparing(ChapterEntity::getChapterNumber));
        if (ObjectUtils.isEmpty(storyEntity)) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.STORY_EMPTY_NOT_RECORD));
            throw new Error400Exception(Constants.E404, errorMessages);
        }

        return storyEntity;
    }
}
