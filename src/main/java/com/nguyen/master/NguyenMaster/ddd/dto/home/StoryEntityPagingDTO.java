package com.nguyen.master.NguyenMaster.ddd.dto.home;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Data
@Setter
@Getter
public class StoryEntityPagingDTO {
    private Integer total;
    private Collection<StoryEntity> storyEntities;
}
