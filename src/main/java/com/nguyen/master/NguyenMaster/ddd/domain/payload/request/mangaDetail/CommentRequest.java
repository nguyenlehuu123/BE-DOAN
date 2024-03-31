package com.nguyen.master.NguyenMaster.ddd.domain.payload.request.mangaDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {
    private String message;
    private String token;
    private BigInteger commentId;
}
