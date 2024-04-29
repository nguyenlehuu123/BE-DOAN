package com.nguyen.master.NguyenMaster.ddd.repositoty.readHistory;


import com.nguyen.master.NguyenMaster.ddd.domain.entity.readHistory.ReadHistoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.readHistory.ReadHistoryId;
import com.nguyen.master.NguyenMaster.ddd.dto.readHistory.ReadHistoryStoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ReadHistoryRepository extends JpaRepository<ReadHistoryEntity, ReadHistoryId> {

    @Query("SELECT new com.nguyen.master.NguyenMaster.ddd.dto.readHistory.ReadHistoryStoryDTO(" +
            "s.storyId, " +
            "s.storyName, " +
            "s.status, " +
            "s.image, " +
            "(SELECT COUNT(c) as totalChapter FROM ChapterEntity c WHERE c.storyEntity.storyId = s.storyId), " +
            "rh.readAt) " +
            "FROM ReadHistoryEntity rh " +
            "JOIN StoryEntity s ON rh.id.storyId = s.storyId " +
            "WHERE rh.id.userId = :userId " +
            "ORDER BY rh.readAt desc ")
    Page<ReadHistoryStoryDTO> findReadHistoryStoryDTOByUserId(@Param("userId") BigInteger userId, Pageable pageable);
}
