package com.nguyen.master.NguyenMaster.core.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nguyen.master.NguyenMaster.core.constant.enums.DeleteFlgEnum;
import com.nguyen.master.NguyenMaster.core.util.JwtUtils;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.AccountRedis;
import com.nguyen.master.NguyenMaster.ddd.domain.entity.BaseEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Timestamp;

@Component
public class AuditingEntityAction<T extends BaseEntity> {
    @Value("${token.login.prefix}")
    private String tokenLoginPrefix;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    private AccountRedis getUserInfo() {
        HttpServletRequest servletRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String authHeader = servletRequest.getHeader("Authorization");
        AccountRedis accountRedis = null;

        String token = StringUtils.isNotEmpty(authHeader) && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
        if (StringUtils.isNotEmpty(token) && jwtUtils.isValidateToken(token)) {
            String userId = jwtUtils.getUserIdFromJwt(token);
            String redisKey = String.format(tokenLoginPrefix, userId, servletRequest.getRemoteAddr());
            ObjectMapper mapper = new ObjectMapper();
            accountRedis = mapper.convertValue(redisTemplate.opsForValue().get(redisKey), AccountRedis.class);
        }
        return accountRedis;
    }

    public void auditingInsertOrUpdate(T entity, boolean isInsert) {
        AccountRedis accountRedis = getUserInfo();
        Timestamp current = new Timestamp(System.currentTimeMillis());

        if (!ObjectUtils.isEmpty(accountRedis) && !ObjectUtils.isEmpty(entity)) {
            if (isInsert) {
                entity.setCreator(accountRedis.getCreator());
                entity.setCreateTimestamp(current);
                entity.setDeleteFlg(DeleteFlgEnum.ACTIVE.getCode());
            }
            entity.setUpdater(accountRedis.getUpdater());
            entity.setUpdateTimestamp(current);
        }
    }

    public void auditingDelete(T entity) {
        if (!ObjectUtils.isEmpty(entity)) {
            entity.setDeleteFlg(DeleteFlgEnum.DELETE.getCode());
        }
    }
}
