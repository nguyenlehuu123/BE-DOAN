package com.nguyen.master.NguyenMaster.ddd.domain.payload.response.home;

import com.nguyen.master.NguyenMaster.ddd.dto.home.StoryEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoryEntityPagingResponse {
    private Integer total;
    private List<StoryEntityDTO> storyEntityDTOS;
}
