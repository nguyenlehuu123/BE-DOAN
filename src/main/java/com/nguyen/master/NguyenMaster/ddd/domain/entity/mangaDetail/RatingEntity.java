package com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class RatingEntity {
    @Id
    @Column(name = "story_id")
    private BigInteger storyId;

    @Column(name = "user_id")
    private BigInteger userId;

    @Column(name = "rating")
    private Integer rating;

    @Override
    public String toString() {
        return "RatingEntity{" +
                "storyId=" + storyId +
                ", userId=" + userId +
                ", rating=" + rating +
                '}';
    }
}
