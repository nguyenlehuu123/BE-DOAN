package com.nguyen.master.NguyenMaster.ddd.domain.entity.follow;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "follow")
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private BigInteger followId;

    @Column(name = "user_id")
    private BigInteger userId;

    @Column(name = "story_id")
    private BigInteger storyId;

    @Override
    public String toString() {
        return "FollowEntity{" +
                "followId=" + followId +
                ", userId=" + userId +
                ", storyId=" + storyId +
                '}';
    }
}
