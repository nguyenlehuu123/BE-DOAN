package com.nguyen.master.NguyenMaster.ddd.domain.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingRequest {
    @Builder.Default
    private Integer pageNum = 1;

    @Builder.Default
    private Integer pageSize = 20;
}

