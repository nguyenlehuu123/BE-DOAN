package com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, BigInteger> {
    StoryEntity findStoryEntitiesByStoryId(BigInteger storyId);

    @Query(value = "select * from story where story_id in (?1) and delete_flg = 0", nativeQuery = true)
    List<StoryEntity> findStoryEntitiesByStoryIds(List<BigInteger> storyIds);

    @Query("SELECT max(s.storyId) FROM StoryEntity s")
    Integer getLastAutoIncrementValue();

    void deleteStoryEntityByStoryId(BigInteger storyId);
}
