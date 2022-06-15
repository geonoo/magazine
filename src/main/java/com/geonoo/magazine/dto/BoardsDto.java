package com.geonoo.magazine.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class BoardsDto {

    private Long userId;

    @NotEmpty(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String body;

    @NotBlank(message = "이미지를 추가해주세요")
    private String img_url;

    private int template;
}
