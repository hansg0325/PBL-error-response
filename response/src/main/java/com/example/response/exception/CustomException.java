package com.example.response.exception;

import com.example.response.entity.ErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;

public class CustomException extends RuntimeException {
    @Getter
    private final ErrorCode errorCode;
    private String message;

    // 타입은 어떤 것이든 상관 없음
    @Getter
    private Map.Entry<String, Object> data;

    @Override
    public String getMessage() {
        if (StringUtils.hasLength(this.message)) {
            return this.message;
        }
        return errorCode.getMessage();
    }

    public CustomException(ErrorCode errorCode, String message, Object data) {
        this.errorCode = errorCode;
        this.message = message;
        this.data = new AbstractMap.SimpleEntry<>(data.getClass().getSimpleName(), data);
    }
}
