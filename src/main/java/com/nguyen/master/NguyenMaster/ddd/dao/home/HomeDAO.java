package com.nguyen.master.NguyenMaster.ddd.dao.home;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.OptionHeaderEntity;

import java.util.List;

public interface HomeDAO {
    List<OptionHeaderEntity> findAllOptionHeaderEntity();
}
