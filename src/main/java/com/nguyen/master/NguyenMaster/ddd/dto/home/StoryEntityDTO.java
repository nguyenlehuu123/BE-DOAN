package com.nguyen.master.NguyenMaster.ddd.dto.home;

import lombok.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryEntityDTO {
    private BigInteger storyId;
    private String storyName;
    private Integer status;
    private String image;
    private Timestamp updateTimestamp;
    private Long totalChapter;
}
