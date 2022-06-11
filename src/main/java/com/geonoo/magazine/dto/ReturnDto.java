package com.geonoo.magazine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReturnDto {
    private String result;
    private String message;
    private Object data;
}
