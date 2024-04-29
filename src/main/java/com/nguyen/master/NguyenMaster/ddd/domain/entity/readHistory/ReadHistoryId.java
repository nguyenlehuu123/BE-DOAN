package com.nguyen.master.NguyenMaster.ddd.domain.entity.readHistory;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class ReadHistoryId implements Serializable {
    private Long storyId;
    private Long userId;
}
