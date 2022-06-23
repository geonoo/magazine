package com.geonoo.magazine.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsersDtoTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @DisplayName("정상")
    @Test
    void normal() {
        //given
        UsersDto usersDto = UsersDto.builder()
                .email("geonoo@naver.com")
                .password("qaz!@#edc123")
                .username("나는나야")
                .build();
        //when
        Set<ConstraintViolation<UsersDto>> violations = validator.validate(usersDto);
        //then
        assertEquals(0, violations.size());
    }
    @DisplayName("(실패)이메일 빈 값")
    @Test
    void emailCheck1(){
        //given
        UsersDto usersDto = UsersDto.builder()
                .email("")
                .password("qaz!@#edc123")
                .username("나는나야")
                .build();
        //when
        Set<ConstraintViolation<UsersDto>> violations = validator.validate(usersDto);
        //then
        for (ConstraintViolation<UsersDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
        assertEquals(1, violations.size());
    }

    @DisplayName("(실패)이메일 형식")
    @Test
    void emailCheck2(){
        //given
        UsersDto usersDto = UsersDto.builder()
                .email("aaa@@?")
                .password("qaz!@#edc123")
                .username("나는나야")
                .build();
        //when
        Set<ConstraintViolation<UsersDto>> violations = validator.validate(usersDto);
        //then
        for (ConstraintViolation<UsersDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
        assertEquals(1, violations.size());
    }

    @DisplayName("(실패)비밀번호 형식")
    @Test
    void password_check1(){
        //given
        UsersDto usersDto = UsersDto.builder()
                .email("geonoo@naver.com")
                .password("1234")
                .username("나는나야")
                .build();
        //when
        Set<ConstraintViolation<UsersDto>> violations = validator.validate(usersDto);
        //then
        for (ConstraintViolation<UsersDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
        assertEquals(1, violations.size());
    }

    @DisplayName("(실패)이메일 값 안에 비밀번호 포함")
    @Test
    void password_check2(){
        //given
        UsersDto usersDto = UsersDto.builder()
                .email("geonoo@naver.com")
                .password("geonoo123@@")
                .username("나는나야")
                .build();
        //when
        Set<ConstraintViolation<UsersDto>> violations = validator.validate(usersDto);
        //then
        for (ConstraintViolation<UsersDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
        assertEquals(1, violations.size());
    }

    @DisplayName("(실패)닉네임 형식")
    @Test
    void nick_check1(){
        //given
        UsersDto usersDto = UsersDto.builder()
                .email("geonoo@naver.com")
                .password("qqqq3333@@@@")
                .username("@@")
                .build();
        //when
        Set<ConstraintViolation<UsersDto>> violations = validator.validate(usersDto);
        //then
        for (ConstraintViolation<UsersDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
        assertEquals(1, violations.size());
    }

    @DisplayName("(실패) 여러개 빈값")
    @Test
    void many_fail(){
        //given
        UsersDto usersDto = UsersDto.builder()
                .email("")
                .password("")
                .username("")
                .build();
        //when
        Set<ConstraintViolation<UsersDto>> violations = validator.validate(usersDto);
        //then
        for (ConstraintViolation<UsersDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
        assertEquals(5, violations.size());
    }

}