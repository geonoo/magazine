package com.geonoo.magazine.dto;

import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@AllArgsConstructor
public class UsersDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,16}",
            message = "비밀번호는 4~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;


    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String username;

    @AssertTrue(message = "비밀번호안에 이메일값이 포함되어 있습니다.") // return true면 포함X false면 포함
    public boolean isEmailInPassword() {
        return !this.password.contains(this.email.split("@")[0]);
    }
}