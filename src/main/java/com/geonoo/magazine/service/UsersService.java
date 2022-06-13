package com.geonoo.magazine.service;

import com.geonoo.magazine.dto.UsersDto;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    @Transactional
    public String saveUser(Users users){
        if (checkEmailDuple(users.getEmail())){
            return "이미 사용중인 이메일 입니다";
        }else{
            usersRepository.save(users);
            return "회원가입완료";
        }
    }
    public boolean checkEmailDuple(String email){
        return usersRepository.existsByEmail(email);
    }
}
