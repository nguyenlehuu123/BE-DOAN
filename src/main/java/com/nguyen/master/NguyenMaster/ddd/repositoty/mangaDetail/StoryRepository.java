package com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, BigInteger> {
    StoryEntity findStoryEntitiesByStoryId(BigInteger storyId);
}
