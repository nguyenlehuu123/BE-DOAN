package com.nguyen.master.NguyenMaster.ddd.usecase.home;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AuthorEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.OptionHeaderEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.PagingRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.home.SearchAllStory;
import com.nguyen.master.NguyenMaster.ddd.dto.home.StoryEntityPagingDTO;
import com.nguyen.master.NguyenMaster.ddd.repositoty.home.HomeRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.home.OptionHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetStoryHotService extends BaseService {

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private OptionHeaderRepository optionHeaderRepository;

    public List<OptionHeaderEntity> getAllOptionHeader() {
        List<OptionHeaderEntity> optionHeaderEntities = optionHeaderRepository.findAllByOrderByOrderSortAsc();
        return optionHeaderEntities;
    }

    public Collection<StoryEntity> getStoryHot() {
        Collection<StoryEntity> storyEntities = homeRepository.findAllStoryHot(16);
        if (CollectionUtils.isEmpty(storyEntities)) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.NOT_STORY_HOT));
            throw new Error400Exception(Constants.E404, errorMessages);
        }
        return storyEntities;
    }

    public StoryEntityPagingDTO getStoryUpdateNew(PagingRequest pagingRequest) {
        int total = (int) homeRepository.count();

        if (total == 0) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.STORY_EMPTY_NOT_RECORD));
            throw new Error400Exception(Constants.E400, errorMessages);
        }

        Collection<StoryEntity> storyEntities = homeRepository.findAllStoryUpdateNew(pagingRequest);
        if (CollectionUtils.isEmpty(storyEntities)) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.NOT_STORY_HOT));
            throw new Error400Exception(Constants.E404, errorMessages);
        }

        StoryEntityPagingDTO storyEntityPagingDTOS = new StoryEntityPagingDTO();
        storyEntityPagingDTOS.setTotal(total);
        storyEntityPagingDTOS.setStoryEntities(storyEntities);
        return storyEntityPagingDTOS;
    }

    public List<SearchAllStory> searchAllStory() {
        Collection<StoryEntity> storyEntities = homeRepository.findAllBy();
        List<SearchAllStory> searchAllStories = new ArrayList<>();
        for(StoryEntity storyEntity : storyEntities) {
            SearchAllStory searchAllStory = new SearchAllStory();
            searchAllStory.setStoryId(storyEntity.getStoryId());
            searchAllStory.setStoryName(storyEntity.getStoryName());
            searchAllStory.setImageStory(storyEntity.getImage());
            searchAllStory.setNameAuthor(storyEntity.getAuthorEntities().stream().map(AuthorEntity::getName).collect(Collectors.joining(", ")));
            searchAllStories.add(searchAllStory);
        }
        return searchAllStories;
    }

}
