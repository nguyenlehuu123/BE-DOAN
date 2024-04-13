package com.nguyen.master.NguyenMaster.ddd.usecase.uploadStory;

import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AuthorEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.uploadStory.SelectAuthorResponse;
import com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllAuthorService extends BaseService {
    @Autowired
    private AuthorRepository authorRepository;


    public List<SelectAuthorResponse> getAllAuthor() {
        List<AuthorEntity> authorEntities = authorRepository.findAll();
        List<SelectAuthorResponse> selectAuthorResponses = new ArrayList<>();
        for (AuthorEntity authorEntity : authorEntities) {
            SelectAuthorResponse selectAuthorResponse = SelectAuthorResponse.builder()
                    .authorId(authorEntity.getAuthorId())
                    .authorName(authorEntity.getPseudonym())
                    .build();
            selectAuthorResponses.add(selectAuthorResponse);
        }
        return selectAuthorResponses;
    }
}
