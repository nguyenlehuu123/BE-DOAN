package com.nguyen.master.NguyenMaster.ddd.domain.payload.request.uploadStory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterAddRequest {
    private Integer chapterNumber;
    private Integer statusKey;
    private String releaseDate;
    private String urlFile;
    private String fileName;
}
