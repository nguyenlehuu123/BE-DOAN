package com.nguyen.master.NguyenMaster.ddd.domain.entity.follow;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "follow")
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Integer followId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "story_id")
    private Integer storyId;

    @Override
    public String toString() {
        return "FollowEntity{" +
                "followId=" + followId +
                ", userId=" + userId +
                ", storyId=" + storyId +
                '}';
    }
}
