package com.nguyen.master.NguyenMaster.ddd.domain.payload.response.home;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionHeaderResponse {
    private Integer optionHeaderId;

    private String title;

    private Integer orderSort;

    private String link;
}
