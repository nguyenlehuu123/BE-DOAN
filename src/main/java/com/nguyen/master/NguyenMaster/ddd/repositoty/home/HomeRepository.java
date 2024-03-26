package com.nguyen.master.NguyenMaster.ddd.repositoty.home;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.PagingRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.home.SearchAllStory;
import com.nguyen.master.NguyenMaster.ddd.dto.home.StoryEntityPagingDTO;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;

@Repository
public interface HomeRepository extends JpaRepository<StoryEntity, BigInteger> {

    @Query(value = "select * from story as s " +
            "where s.status = 1 and s.delete_flg = 0 order by s.update_timestamp, s.follow_number " +
            "limit ?1", nativeQuery = true)
    Collection<StoryEntity> findAllStoryHot(@Param("limit") Integer limit);

    @Query(value = "select * from story as s " +
            "where s.delete_flg = 0 " +
            "order by s.update_timestamp, s.create_timestamp " +
            "limit :#{#request.pageSize} offset (:#{#request.pageNum} - 1) * (:#{#request.pageSize}) ", nativeQuery = true)
    Collection<StoryEntity> findAllStoryUpdateNew(@Param("request") PagingRequest request);

    Collection<StoryEntity> findAllBy();
}
