package com.nguyen.master.NguyenMaster.ddd.repositoty.follow;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.follow.FollowEntity;
import com.nguyen.master.NguyenMaster.ddd.dto.home.StoryEntityDTO;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Collection;

public interface FollowRepository extends JpaRepository<FollowEntity, BigInteger> {
    @Modifying
    @Query(value = "insert into follow (user_id, story_id) " +
            "values (:userId, :storyId)", nativeQuery = true)
    void insertFollowEntity(BigInteger userId, BigInteger storyId);

    @Modifying
    @Query(value = "delete from follow where user_id = :userId and story_id = :storyId", nativeQuery = true)
    void deleteFollowEntitiesByUserIdAndStoryId(BigInteger userId, BigInteger storyId);

    @Query("SELECT new com.nguyen.master.NguyenMaster.ddd.dto.home.StoryEntityDTO(" +
            "s.storyId, " +
            "s.storyName, " +
            "s.status, " +
            "s.image, " +
            "s.updateTimestamp, " +
            "(SELECT COUNT(c) as totalChapter FROM ChapterEntity c WHERE c.storyEntity.storyId = s.storyId )) " +
            "FROM FollowEntity as f " +
            "INNER JOIN StoryEntity as s ON s.storyId = f.storyId " +
            "INNER JOIN Users as u ON f.userId = u.userId " +
            "WHERE s.deleteFlg = 0 AND u.userId = :userId " )
    Collection<StoryEntityDTO> findFollowEntitiesByUserId(@Param("useId") BigInteger userId);

    FollowEntity findFollowEntitiesByUserIdAndStoryId(BigInteger userId, BigInteger storyId);

    void deleteFollowEntitiesByStoryId(BigInteger storyId);
    void deleteFollowEntitiesByUserId(BigInteger userId);
}
