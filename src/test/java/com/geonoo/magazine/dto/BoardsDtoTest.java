package com.geonoo.magazine.dto;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geonoo.magazine.validate.BoardValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class BoardsDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ResourceLoader loader;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("정상-게시물")
    @Test
    void normal_board() throws JsonProcessingException {
        //given
        String board = "{\"title\":\"나는 제목 꿈을 꾸는 제목\", "
                + "\"body\":\"나는 내용 꿈을 꾸는 내용\","
                + "\"template\":\"1\""
                + "}";
        //when
        BoardsDto boardsDto = objectMapper.readValue(board, BoardsDto.class);
        BoardValidation.checkEmpty(boardsDto);
        //then
    }

    @DisplayName("정상 이미지")
    @Test
    void normal_image() throws IOException {
        //given
        InputStream is = new BufferedInputStream(
                new FileInputStream("src/main/resources/static/휴식.png")
        );
        MockMultipartFile file = new MockMultipartFile("휴식", "휴식.png", "image/png", is);
        List<MultipartFile> fileList = new ArrayList<>();
        fileList.add(file);
        System.out.println(file.getSize());
        //when
        BoardValidation.checkMultiPart(fileList);
        //then
    }

    @DisplayName("실패 제목 빈 값")
    @Test
    void fail_title() throws JsonProcessingException {
        //given
        String board = "{\"title\":\"\","
                + "\"body\":\"나는 내용 꿈을 꾸는 내용\","
                + "\"template\":\"1\""
                + "}";
        //when
        BoardsDto boardsDto = objectMapper.readValue(board, BoardsDto.class);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BoardValidation.checkEmpty(boardsDto);
        });
    }

    @DisplayName("실패 내용 널 값")
    @Test
    void fail_body() throws JsonProcessingException {
        //given
        String board = "{\"title\":\"미안에 솔직하지 못한 내가\",\"template\":\"1\"}";
        //when
        BoardsDto boardsDto = objectMapper.readValue(board, BoardsDto.class);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BoardValidation.checkEmpty(boardsDto);
        });
    }

    @DisplayName("실패 이미지 size 0")
    @Test
    void fail_image() {
        //given
        List<MultipartFile> fileList = new ArrayList<>();
        //when

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BoardValidation.checkMultiPart(fileList);
        });
    }

    @DisplayName("실패 이미지 null")
    @Test
    void fail_image2() {
        //given
        List<MultipartFile> fileList = null;
        //when

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BoardValidation.checkMultiPart(fileList);
        });
    }


}