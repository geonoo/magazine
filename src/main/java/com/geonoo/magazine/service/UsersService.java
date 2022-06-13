package com.geonoo.magazine.service;

import com.geonoo.magazine.dto.UsersDto;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.UsersRepository;
import com.geonoo.magazine.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;

    @Transactional
    public String saveUser(Users users){
        if (checkEmailDuple(users.getEmail())){
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다");
        }else{
            usersRepository.save(users);
            return "회원가입완료";
        }
    }
    public boolean checkEmailDuple(String email){
        return usersRepository.existsByEmail(email);
    }

    public Map<String, String> login(UsersDto usersDto) {
        Map<String, String> token = new HashMap<>();

        Users users = usersRepository.findByEmail(usersDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 패스워드를 확인해주세요"));

        if (!passwordEncoder.matches(usersDto.getPassword(), users.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 패스워드를 확인해주세요");
        }

        token.put("Access-Token", jwtTokenProvider.createAccessToken(users));

        return token;
    }
}
