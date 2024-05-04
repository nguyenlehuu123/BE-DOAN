package com.nguyen.master.NguyenMaster.ddd.usecase.userManagement;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.auth.Users;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.PagingRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.userManagement.UpdateRoleRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.response.userManagement.UserManagementResponse;
import com.nguyen.master.NguyenMaster.ddd.dto.userManagement.UserManagementDTO;
import com.nguyen.master.NguyenMaster.ddd.repositoty.auth.TokenRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.auth.UserRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.follow.FollowRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.CommentRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.LikesRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.mangaDetail.RatingRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.readHistory.ReadHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class GetUserService extends BaseService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private ReadHistoryRepository readHistoryRepository;

    @Autowired
    private CommentRepository commentRepository;

    public UserManagementResponse getUser(PagingRequest pagingRequest) {
        PageRequest pageRequest = PageRequest.of(pagingRequest.getPageNum() - 1, pagingRequest.getPageSize());
        Page<UserManagementDTO> userManagementDTOS = userRepository.findUserManagementPaging(pageRequest);

        UserManagementResponse userManagementResponses = new UserManagementResponse();
        userManagementResponses.setTotal((int) userManagementDTOS.getTotalElements());
        userManagementResponses.setUserManagementDTOS(userManagementDTOS.getContent());
        return userManagementResponses;
    }

    public NormalDefaultResponse deleteUser(BigInteger userId) {
        Users user = userRepository.findUserByUserId(userId);
        if (user == null) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.ERROR_RESPONSE));
            throw new Error400Exception(Constants.E404, errorMessages);
        }

        // Delete ratings associated with the user first
        ratingRepository.deleteRatingEntitiesByIdUserId(userId); // This is an example. Replace with your actual method to delete ratings.

        tokenRepository.deleteTokensByUsers_UserId(userId);

        followRepository.deleteFollowEntitiesByUserId(userId);

        likesRepository.deleteLikesEntitiesByUserId(userId);

        readHistoryRepository.deleteReadHistoryEntitiesByIdUserId(userId);

        commentRepository.deleteCommentEntitiesByUsers_UserId(userId);
        // Then delete the user
        userRepository.deleteUserByUserId(userId);

        NormalDefaultResponse normalDefaultResponse = new NormalDefaultResponse();
        normalDefaultResponse.setMessage(SystemMessageCode.SUCCESS_PROCESS);
        return normalDefaultResponse;
    }

    public NormalDefaultResponse updateRoleUser(BigInteger userId, UpdateRoleRequest updateRoleRequest) {
        Users user = userRepository.findUserByUserId(userId);
        if (user == null) {
            List<ErrorMessage> errorMessages = List.of(buildErrorMessage(SystemMessageCode.ERROR_RESPONSE));
            throw new Error400Exception(Constants.E404, errorMessages);
        }

        user.setRole(updateRoleRequest.getRole());
        userRepository.save(user);

        NormalDefaultResponse normalDefaultResponse = new NormalDefaultResponse();
        normalDefaultResponse.setMessage(SystemMessageCode.SUCCESS_PROCESS);
        return normalDefaultResponse;
    }
}
