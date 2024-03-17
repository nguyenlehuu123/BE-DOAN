package com.nguyen.master.NguyenMaster.core.util.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter {

    public <T> T convertValueToObject(Object fromValue, Class<T> toValueType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.convertValue(fromValue, toValueType);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
