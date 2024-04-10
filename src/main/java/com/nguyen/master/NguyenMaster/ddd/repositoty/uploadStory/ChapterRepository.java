package com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<ChapterEntity, Integer> {
    ChapterEntity findChapterEntitiesByChapterId(Integer chapterId);

    List<ChapterEntity> deleteChapterEntitiesByStoryEntity(StoryEntity storyEntity);
}
