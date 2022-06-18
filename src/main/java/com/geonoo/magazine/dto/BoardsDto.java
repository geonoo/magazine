package com.geonoo.magazine.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
public class BoardsDto {

    @NotEmpty(message = "제목을 입력해주세요")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요")
    private String body;

    private String img_url;

    private int template;

    @Builder
    BoardsDto(String title, String body, String img_url, int template){
        this.title = title;
        this.body = body;
        this.img_url = img_url;
        this.template = template;

    }
    BoardsDto(){

    }
}
