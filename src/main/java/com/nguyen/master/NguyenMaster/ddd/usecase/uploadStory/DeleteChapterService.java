package com.nguyen.master.NguyenMaster.ddd.usecase.uploadStory;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.mangaDetail.ChapterEntity;
import com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeleteChapterService extends BaseService {
    @Autowired
    private ChapterRepository chapterRepository;

    public NormalDefaultResponse deleteChapter(Integer chapterId) {
        Optional<ChapterEntity> chapterEntity = chapterRepository.findById(chapterId);
        if (chapterEntity.isEmpty()) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.CHAPTER_NOT_EXITS));
            throw new Error400Exception(Constants.E404, errorMessages);
        }
        chapterRepository.deleteById(chapterId);

        NormalDefaultResponse normalDefaultResponse = new NormalDefaultResponse();
        normalDefaultResponse.setMessage(SystemMessageCode.SUCCESS_PROCESS);
        return normalDefaultResponse;
    }
}
