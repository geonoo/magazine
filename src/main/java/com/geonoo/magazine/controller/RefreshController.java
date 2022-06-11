package com.geonoo.magazine.controller;

import com.geonoo.magazine.dto.ReturnDto;
import com.geonoo.magazine.dto.UsersDto;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.UsersRepository;
import com.geonoo.magazine.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RefreshController {

    private final JwtTokenProvider jwtTokenProvider;

    private final UsersRepository usersRepository;

    @PostMapping("/api/refresh-token")
    public ReturnDto refreshToken(ServletRequest request, @RequestBody UsersDto usersDto){
        Map<String, String> token = new HashMap<>();
        ReturnDto returnDto;
        String refreshToken = jwtTokenProvider.refreshResolveToken((HttpServletRequest) request);
        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {

            Users member = usersRepository.findByEmail(usersDto.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

            token.put("Access-Token", jwtTokenProvider.createAccessToken(member, member.getRoles()));
            token.put("Refresh-Token", jwtTokenProvider.createRefreshToken());
            returnDto = new ReturnDto("OK", "토큰생성완료",token);
        }else{
            returnDto = new ReturnDto("Fail", "다시 로그인 해주세요", token);
        }
        return returnDto;
    }
}
