package com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface StoryGenreRepository extends JpaRepository<StoryGenreEntity, Integer> {
    List<StoryGenreEntity> findAll();

    StoryGenreEntity findStoryGenreEntitiesByStoryGenreId(Integer storyGenreId);
}
