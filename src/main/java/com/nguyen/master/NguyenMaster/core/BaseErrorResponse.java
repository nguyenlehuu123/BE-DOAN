package com.nguyen.master.NguyenMaster.core;

import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseErrorResponse<T> {
    @Builder.Default
    private String errorCode = "default";

    @Builder.Default
    private List<ErrorMessage> errorMessages = List.of(new ErrorMessage("defaultKey", "defaultMessage"));

    private T data;

    @Builder.Default
    private long responseTime = System.currentTimeMillis();
}
