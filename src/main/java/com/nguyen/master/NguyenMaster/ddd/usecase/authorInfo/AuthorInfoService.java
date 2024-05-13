package com.nguyen.master.NguyenMaster.ddd.usecase.authorInfo;

import com.nguyen.master.NguyenMaster.core.NormalDefaultResponse;
import com.nguyen.master.NguyenMaster.core.common.BaseService;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.constant.SystemMessageCode;
import com.nguyen.master.NguyenMaster.core.constant.enums.Role;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.Error400Exception;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AuthorEntity;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.auth.Users;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.authorInfo.AuthorInfoRequest;
import com.nguyen.master.NguyenMaster.ddd.domain.payload.request.EmailRequest;
import com.nguyen.master.NguyenMaster.ddd.repositoty.auth.UserRepository;
import com.nguyen.master.NguyenMaster.ddd.repositoty.uploadStory.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorInfoService extends BaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorEntity getAuthorInfo(EmailRequest email) {
        Optional<Users> user = userRepository.findByEmail(email.getEmail());
        if (user.isEmpty()) {
            List<ErrorMessage> errorMessage = List.of(buildErrorMessage(SystemMessageCode.USER_NOT_EXITS));
            throw new Error400Exception(Constants.E400, errorMessage);
        }

        AuthorEntity authorEntity = authorRepository.findAuthorEntitiesByEmail(user.get().getEmail());
        if (ObjectUtils.isEmpty(authorEntity)) {
            AuthorEntity authorEntity1 = new AuthorEntity();
            authorEntity1.setEmail(user.get().getEmail());
            return authorEntity1;
        } else {
            return authorEntity;
        }
    }

    public NormalDefaultResponse saveAuthorInfo(AuthorInfoRequest authorInfoRequest) {
        AuthorEntity authorEntity = authorRepository.findAuthorEntitiesByEmail(authorInfoRequest.getEmail());
        if (ObjectUtils.isEmpty(authorEntity)) {
            authorEntity = new AuthorEntity();
            authorEntity.setEmail(authorInfoRequest.getEmail());
            authorEntity.setName(authorInfoRequest.getName());
            authorEntity.setAddress(authorInfoRequest.getAddress());
            authorEntity.setPhone(authorInfoRequest.getPhone());
            authorEntity.setPseudonym(authorInfoRequest.getPseudonym());
            authorEntity.setDateOfBirth(authorInfoRequest.getDateOfBirth());
            authorRepository.save(authorEntity);
        } else {
            authorEntity.setName(authorInfoRequest.getName());
            authorEntity.setAddress(authorInfoRequest.getAddress());
            authorEntity.setPhone(authorInfoRequest.getPhone());
            authorEntity.setPseudonym(authorInfoRequest.getPseudonym());
            authorEntity.setDateOfBirth(authorInfoRequest.getDateOfBirth());
            authorRepository.save(authorEntity);
        }

        Optional<Users> user = userRepository.findByEmail(authorEntity.getEmail());
        if (user.isEmpty()) {
            List<ErrorMessage> errorMessage = List.of(buildErrorMessage(SystemMessageCode.USER_NOT_EXITS));
            throw new Error400Exception(Constants.E400, errorMessage);
        }
        user.get().setRole(Role.AUTHOR);
        userRepository.save(user.get());

        NormalDefaultResponse normalDefaultResponse = new NormalDefaultResponse();
        normalDefaultResponse.setMessage(getMessage(SystemMessageCode.SUCCESS_PROCESS));
        return normalDefaultResponse;
    }
}
