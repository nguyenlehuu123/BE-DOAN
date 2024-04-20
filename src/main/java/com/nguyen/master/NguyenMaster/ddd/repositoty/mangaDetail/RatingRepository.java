package com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, BigInteger> {
    List<RatingEntity> findRatingEntitiesByStoryId(BigInteger storyId);

    RatingEntity findRatingEntityByStoryIdAndUserId(BigInteger storyId, BigInteger userId);
}
