package com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class RatingId implements Serializable {
    @Column(name = "story_id")
    private BigInteger storyId;

    @Column(name = "user_id")
    private BigInteger userId;
}
