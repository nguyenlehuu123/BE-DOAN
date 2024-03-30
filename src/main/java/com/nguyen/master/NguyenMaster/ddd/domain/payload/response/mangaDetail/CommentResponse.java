package com.nguyen.master.NguyenMaster.ddd.domain.payload.response.mangaDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private BigInteger commentId;
    private String message;
    private Timestamp timeComment;
    private Integer likeComment;
    private Integer dislikeComment;
    private String emailUserComment;
    private String avatar;
}
