package com.nguyen.master.NguyenMaster.ddd.usecase.readHistory;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.AuditingEntityAction;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AccountRedis;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.home.StoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.readHistory.ReadHistoryEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.readHistory.ReadHistoryId;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.PagingRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.readHistoty.ReadHistoryStoryResponse;
import com.nguyen.master.NguyenMaster.ddd.dto.readHistory.ReadHistoryStoryDTO;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.StoryRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.readHistory.ReadHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ReadHistoryService extends BaseService {

    @Autowired
    private ReadHistoryRepository readHistoryRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private AuditingEntityAction auditingEntityAction;

    public ReadHistoryStoryResponse getReadHistory(PagingRequest pagingRequest) {
        AccountRedis accountRedis = auditingEntityAction.getUserInfo();
        BigInteger userId = accountRedis.getUserId();

        PageRequest pageRequest = PageRequest.of(pagingRequest.getPageNum() - 1, pagingRequest.getPageSize());
        Page<ReadHistoryStoryDTO> readHistoryEntities = readHistoryRepository.findReadHistoryStoryDTOByUserId(userId, pageRequest);

        ReadHistoryStoryResponse readHistoryStoryResponse = new ReadHistoryStoryResponse();
        readHistoryStoryResponse.setTotal((int) readHistoryEntities.getTotalElements());
        readHistoryStoryResponse.setReadHistoryStoryDTOs(readHistoryEntities.getContent());
        return readHistoryStoryResponse;
    }

    public NormalDefaultResponse addReadHistory(BigInteger storyId) {
        AccountRedis accountRedis = auditingEntityAction.getUserInfo();
        if (!ObjectUtils.isEmpty(accountRedis)) {
            BigInteger userId = accountRedis.getUserId();

            StoryEntity storyEntity = storyRepository.findById(storyId).orElse(null);
            if (storyEntity == null) {
                List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.STORY_EMPTY_NOT_RECORD));
                throw new Error400Exception(Constants.E404, errorMessages);
            }

            ReadHistoryEntity readHistoryEntity = new ReadHistoryEntity();
            readHistoryEntity.setId(ReadHistoryId.builder().userId(userId).storyId(storyId).build());
            readHistoryEntity.setReadAt(new Timestamp(System.currentTimeMillis()));
            readHistoryRepository.save(readHistoryEntity);
        }

        NormalDefaultResponse normalDefaultResponse = new NormalDefaultResponse();
        normalDefaultResponse.setMessage(getMessage(SystemMessageCode.SUCCESS_PROCESS));
        return normalDefaultResponse;
    }
}
