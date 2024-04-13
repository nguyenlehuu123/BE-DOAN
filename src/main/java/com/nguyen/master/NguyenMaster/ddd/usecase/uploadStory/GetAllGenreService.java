package com.nguyen.master.NguyenMaster.ddd.usecase.uploadStory;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryGenreEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.uploadStory.StoryGenreResponse;
import com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory.StoryGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllGenreService extends BaseService {
    @Autowired
    private StoryGenreRepository storyGenreRepository;

    public List<StoryGenreResponse> getAllStoryGenre() {
        List<StoryGenreEntity> storyGenreEntities = storyGenreRepository.findAll();
        List<StoryGenreResponse> storyGenreResponses = new ArrayList<>();
        for (StoryGenreEntity storyGenre : storyGenreEntities) {
            StoryGenreResponse storyGenreResponse = StoryGenreResponse.builder()
                    .storyGenreId(storyGenre.getStoryGenreId())
                    .storyGenreName(storyGenre.getStoryGenreName())
                    .build();
            storyGenreResponses.add(storyGenreResponse);
        }
        return storyGenreResponses;
    }
}
