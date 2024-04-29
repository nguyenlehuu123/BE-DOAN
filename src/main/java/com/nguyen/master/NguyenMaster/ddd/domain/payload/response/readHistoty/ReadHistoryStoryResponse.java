package com.nguyen.master.NguyenMaster.ddd.domain.payload.response.readHistoty;

import com.nguyen.master.NguyenMaster.ddd.dto.readHistory.ReadHistoryStoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadHistoryStoryResponse {
    private Integer total;
    private List<ReadHistoryStoryDTO> readHistoryStoryDTOs;
}
