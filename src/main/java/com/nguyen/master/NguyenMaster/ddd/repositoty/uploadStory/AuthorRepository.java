package com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory;

import com.nguyen.master.NguyenMaster.ddd.domain.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, BigInteger> {
    AuthorEntity findAuthorEntityByAuthorId(BigInteger authorId);
    AuthorEntity findAuthorEntitiesByEmail(String email);

    void deleteAuthorEntitiesByEmail(String email);
}
