package com.nguyen.master.NguyenMaster.ddd.dao.home;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.OptionCategoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.OptionHeaderEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HomeDAOImpl implements HomeDAO{
    @Autowired
    private EntityManager entityManager;

    @Override
    public  List<OptionHeaderEntity> findAllOptionHeaderEntity() {
        TypedQuery<OptionHeaderEntity> theQuery = entityManager.createQuery("select oh from OptionHeaderEntity oh " +
                "inner join fetch oh.optionCategoryEntities oc " +
                "order by oh.orderSort asc", OptionHeaderEntity.class);

        List<OptionHeaderEntity> optionHeaderEntities = theQuery.getResultList();
        return optionHeaderEntities;
    }
}
