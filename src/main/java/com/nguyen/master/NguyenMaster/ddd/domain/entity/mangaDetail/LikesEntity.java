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
@Table(name = "likes")
public class LikesEntity {
    @Id
    @Column(name = "story_id")
    private BigInteger storyId;

    @Column(name = "user_id")
    private BigInteger userId;

    @Override
    public String toString() {
        return "LikesEntity{" +
                "storyId=" + storyId +
                ", userId=" + userId +
                '}';
    }
}
