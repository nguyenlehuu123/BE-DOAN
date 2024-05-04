package com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.RatingEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, RatingId> {
    List<RatingEntity> findRatingEntitiesByIdStoryId(BigInteger storyId);

    Optional<RatingEntity> findById(RatingId id);

    void deleteRatingEntitiesByIdStoryId(BigInteger storyId);

    void deleteRatingEntitiesByIdUserId(BigInteger userId);
}
