package com.nguyen.master.NguyenMaster.ddd.repositoty.follow;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.follow.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface FollowRepository extends JpaRepository<FollowEntity, BigInteger> {
    @Modifying
    @Query(value = "insert into follow (user_id, story_id) " +
            "values (:userId, :storyId)", nativeQuery = true)
    void insertFollowEntity(BigInteger userId, BigInteger storyId);

    @Modifying
    @Query(value = "delete from follow where user_id = :userId and story_id = :storyId", nativeQuery = true)
    void deleteFollowEntitiesByUserIdAndStoryId(BigInteger userId, BigInteger storyId);
}
