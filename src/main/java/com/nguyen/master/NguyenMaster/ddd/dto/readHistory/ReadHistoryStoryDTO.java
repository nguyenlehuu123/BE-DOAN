package com.nguyen.master.NguyenMaster.ddd.dto.readHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadHistoryStoryDTO {
    private BigInteger storyId;
    private String storyName;
    private Integer status;
    private String image;
    private Long totalChapter;
    private Timestamp readAt;
}
