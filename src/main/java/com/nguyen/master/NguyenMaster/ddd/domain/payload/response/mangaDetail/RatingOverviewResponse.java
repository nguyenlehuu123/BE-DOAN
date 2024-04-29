package com.nguyen.master.NguyenMaster.ddd.domain.payload.response.mangaDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingOverviewResponse {
    private Float averageRating;
    private Integer totalRating;
    private Integer total5Star;
    private Integer total4Star;
    private Integer total3Star;
    private Integer total2Star;
    private Integer total1Star;
    private Integer percent5Star;
    private Integer percent4Star;
    private Integer percent3Star;
    private Integer percent2Star;
    private Integer percent1Star;
}
