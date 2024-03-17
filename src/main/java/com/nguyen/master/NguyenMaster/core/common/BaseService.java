package com.nguyen.master.NguyenMaster.core.common;

import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Data
@Transactional(rollbackFor = {Exception.class})
public abstract class BaseService {
    @Resource
    private MessageSource messageSource;

    protected String getMessage(String key, Object... obj) {
        return messageSource.getMessage(key, obj, LocaleContextHolder.getLocale());
    }

    protected ErrorMessage buildErrorMessage(String key, Object... obj) {
        return new ErrorMessage(key, getMessage(key, obj));
    }
}
