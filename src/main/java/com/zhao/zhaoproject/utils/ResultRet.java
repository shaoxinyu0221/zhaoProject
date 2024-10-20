package com.zhao.zhaoproject.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultRet<T> {
    private int code;

    private Boolean success;

    private String message;

    private T data;
}
