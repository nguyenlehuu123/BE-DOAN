package com.nguyen.master.NguyenMaster.ddd.usecase.uploadStory;

import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AuthorEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.uploadStory.SearchStoryRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.uploadStory.SearchStoryResponse;
import com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory.SearchStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchStoryService extends BaseService {
    @Autowired
    private SearchStoryRepository storyRepository;

    public List<SearchStoryResponse> search(SearchStoryRequest request) {
        List<StoryEntity> storyEntities = storyRepository.findByStoryNameAndAuthorName(request.getStoryName(), request.getAuthorName());
        if (CollectionUtils.isEmpty(storyEntities)) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.STORY_EMPTY_NOT_RECORD));
            throw new Error400Exception(Constants.E404, errorMessages);
        }

        List<SearchStoryResponse> searchStoryResponses = new ArrayList<>();
        for (StoryEntity storyEntity : storyEntities) {
            SearchStoryResponse searchStoryResponse = new SearchStoryResponse();
            searchStoryResponse.setStoryId(storyEntity.getStoryId());
            searchStoryResponse.setStoryName(storyEntity.getStoryName());
            searchStoryResponse.setAuthorName(storyEntity.getAuthorEntities().stream().map(AuthorEntity::getPseudonym).toList());
            searchStoryResponse.setDateSubmitted(storyEntity.getUpdateTimestamp());
            searchStoryResponse.setLikeNumber(storyEntity.getLikeNumber());
            searchStoryResponse.setFollowNumber(storyEntity.getFollowNumber());
            searchStoryResponse.setRating(storyEntity.getRatingPoint());
            searchStoryResponses.add(searchStoryResponse);
        }

        return searchStoryResponses;
    }
}
