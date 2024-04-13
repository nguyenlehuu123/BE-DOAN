package com.nguyen.master.NguyenMaster.ddd.domain.payload.response.uploadStory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoryGenreResponse {
    private Integer storyGenreId;
    private String storyGenreName;
}
