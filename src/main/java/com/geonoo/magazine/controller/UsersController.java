package com.geonoo.magazine.controller;

import com.geonoo.magazine.dto.UsersDto;
import com.geonoo.magazine.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UsersController {

    private final UsersService usersService;

    // 회원가입
    @PostMapping("/api/register")
    public String join(@Valid @RequestBody UsersDto usersDto) {
        return usersService.saveUser(usersDto);
    }

    // 로그인
    @PostMapping("/api/login")
    public Map<String,  String> login(@RequestBody UsersDto usersDto) {
        return usersService.login(usersDto);
    }

}
