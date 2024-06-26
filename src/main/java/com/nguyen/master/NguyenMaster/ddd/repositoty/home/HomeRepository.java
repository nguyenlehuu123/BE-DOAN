package com.nguyen.master.NguyenMaster.ddd.repositoty.home;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.PagingRequest;
import com.nguyen.master.NguyenMaster.ddd.dto.home.StoryEntityDTO;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;

@Repository
public interface HomeRepository extends JpaRepository<StoryEntity, BigInteger> {

    @Query("SELECT new com.nguyen.master.NguyenMaster.ddd.dto.home.StoryEntityDTO(" +
            "s.storyId, " +
            "s.storyName, " +
            "s.status, " +
            "s.image, " +
            "s.updateTimestamp, " +
            "(SELECT COUNT(c) as totalChapter FROM ChapterEntity c WHERE c.storyEntity.storyId = s.storyId )) " +
            "FROM StoryEntity as s " +
            "WHERE s.deleteFlg = 0 " +
            "order by s.followNumber desc, s.updateTimestamp desc")
    Collection<StoryEntityDTO> findAllStoryHot(@Param("limit") Integer limit);

    @Query(value = "select * from story as s " +
            "where s.delete_flg = 0 " +
            "order by s.update_timestamp, s.create_timestamp " +
            "limit :#{#request.pageSize} offset (:#{#request.pageNum} - 1) * (:#{#request.pageSize}) ", nativeQuery = true)
    Collection<StoryEntity> findAllStoryUpdateNew(@Param("request") PagingRequest request);

    Collection<StoryEntity> findAllBy();

    Page<StoryEntity> findAllByStoryGenreEntity_StoryGenreId(Integer storyGenreId, Pageable pageable);

    @Query("SELECT new com.nguyen.master.NguyenMaster.ddd.dto.home.StoryEntityDTO(" +
            "s.storyId, " +
            "s.storyName, " +
            "s.status, " +
            "s.image, " +
            "s.updateTimestamp, " +
            "(SELECT COUNT(c) as totalChapter FROM ChapterEntity c WHERE c.storyEntity.storyId = s.storyId )) " +
            "FROM StoryEntity as s " +
            "WHERE s.deleteFlg = 0 " +
            "ORDER BY s.updateTimestamp desc, s.createTimestamp desc")
    Page<StoryEntityDTO> findAllStoryEntityDTO(Pageable pageable);
}
