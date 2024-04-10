package com.nguyen.master.NguyenMaster.ddd.domain.payload.request.uploadStory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsertStoryRequest {
    private String storyName;
    private String releaseDate;
    private String description;
    private String image;
    private List<BigInteger> authorIds;
    private List<ChapterAddRequest> chaptersAdd;
}
