package com.nguyen.master.NguyenMaster.ddd.repositoty.home;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.OptionHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionHeaderRepository extends JpaRepository<OptionHeaderEntity, Integer> {
    List<OptionHeaderEntity> findAllByOrderByOrderSortAsc();
}
