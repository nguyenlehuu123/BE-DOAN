package com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface SearchStoryRepository extends JpaRepository<StoryEntity, BigInteger> {
    @Query("SELECT s FROM StoryEntity s INNER JOIN s.authorEntities a WHERE " +
            "(LOWER(s.storyName) LIKE LOWER(CONCAT('%', :storyName, '%')) OR :storyName IS NULL) AND " +
            "(LOWER(a.pseudonym) LIKE LOWER(CONCAT('%', :authorName, '%')) OR :authorName IS NULL)")
    List<StoryEntity> findByStoryNameAndAuthorName(@Param("storyName") String storyName, @Param("authorName") String authorName);

}
