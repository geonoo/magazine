package com.geonoo.magazine.controller;

import com.geonoo.magazine.dto.ReturnDto;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.UsersRepository;
import com.geonoo.magazine.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UsersController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository userRepository;

    // 회원가입
    @PostMapping("/join")
    public Long join(@RequestBody Map<String, String> user) {
        return userRepository.save(Users.builder()
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .username(user.get("username"))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getUserId();
    }

    // 로그인
    @PostMapping("/login")
    public ReturnDto login(@RequestBody Map<String, String> user) {
        Map<String, String> token = new HashMap<>();

        Users member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        token.put("Access-Token", jwtTokenProvider.createAccessToken(member, member.getRoles()));
        token.put("Refresh-Token", jwtTokenProvider.createRefreshToken());

        return new ReturnDto("OK", "토큰생성완료", token);
    }

    @GetMapping("/user/test")
    public String userTest(){
        return "안녕?";
    }

}
