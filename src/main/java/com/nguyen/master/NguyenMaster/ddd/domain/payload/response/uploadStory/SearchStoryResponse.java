package com.nguyen.master.NguyenMaster.ddd.domain.payload.response.uploadStory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchStoryResponse {
    private BigInteger storyId;
    private String storyName;
    private List<String> authorName;
    private Timestamp dateSubmitted;
    private Integer likeNumber;
    private Integer followNumber;
    private Integer rating;
}
