package com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, BigInteger> {
    @Query(value = "WITH RECURSIVE CommentHierarchy AS (\n" +
            "    SELECT co.*, co.comment_id AS Level\n" +
            "    FROM comment as co\n" +
            "    WHERE co.sub_comment_id IS NULL\n" +
            "\n" +
            "    UNION ALL\n" +
            "\n" +
            "    SELECT c.*, ch.Level\n" +
            "    FROM comment c\n" +
            "    INNER JOIN CommentHierarchy ch ON c.sub_comment_id = ch.comment_id\n" +
            ")\n" +
            "SELECT * FROM CommentHierarchy\n" +
            "WHERE story_id = ?1\n" +
            "ORDER BY Level, comment_id;", nativeQuery = true)
    List<CommentEntity> findCommentEntitiesByStoryId(BigInteger storyId);
}
