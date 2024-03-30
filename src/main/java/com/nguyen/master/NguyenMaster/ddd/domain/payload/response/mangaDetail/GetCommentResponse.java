package com.nguyen.master.NguyenMaster.ddd.domain.payload.response.mangaDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetCommentResponse extends CommentResponse {
    List<CommentResponse> commentResponses;
}
