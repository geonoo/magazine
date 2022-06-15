package com.geonoo.magazine.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReturnDto {
    private String result;
    private String message;
    private Object data;
}
