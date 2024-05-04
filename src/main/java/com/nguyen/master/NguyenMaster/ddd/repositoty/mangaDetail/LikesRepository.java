package com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.LikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, BigInteger> {
    LikesEntity findLikesEntityByStoryIdAndUserId(BigInteger storyId, BigInteger userId);

    void deleteLikesEntitiesByStoryId(BigInteger storyId);

    void deleteLikesEntitiesByUserId(BigInteger userId);
}
