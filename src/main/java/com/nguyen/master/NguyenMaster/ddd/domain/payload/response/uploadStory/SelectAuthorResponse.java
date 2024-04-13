package com.nguyen.master.NguyenMaster.ddd.domain.payload.response.uploadStory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectAuthorResponse {
    private BigInteger authorId;
    private String authorName;
}
