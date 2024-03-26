package com.nguyen.master.NguyenMaster.ddd.domain.payload.response.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchAllStory {
    private BigInteger storyId;
    private String storyName;
    private String imageStory;
    private String nameAuthor;
}
