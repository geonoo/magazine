package com.geonoo.magazine.controller;

import com.geonoo.magazine.dto.UsersDto;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.security.JwtTokenProvider;
import com.geonoo.magazine.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class UsersController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final UsersService usersService;

    // 회원가입
    @PostMapping("/api/register")
    public String join(@Valid @RequestBody UsersDto usersDto) {
        Users users = Users.builder()
                .email(usersDto.getEmail())
                .password(passwordEncoder.encode(usersDto.getPassword()))
                .username(usersDto.getUsername())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        return usersService.saveUser(users);
    }

    // 로그인
//    @PostMapping("/login")
//    public ReturnDto login(@RequestBody Map<String, String> user) {
//        Map<String, String> token = new HashMap<>();
//
//        Users member = userRepository.findByEmail(user.get("email"))
//                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 패스워드를 확인해주세요"));
//        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
//            throw new IllegalArgumentException("이메일 또는 패스워드를 확인해주세요");
//        }
//
//        token.put("Access-Token", jwtTokenProvider.createAccessToken(member, member.getRoles()));
//        token.put("Refresh-Token", jwtTokenProvider.createRefreshToken());
//
//        return new ReturnDto("OK", "토큰생성완료", token);
//    }

}
