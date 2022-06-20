package com.geonoo.magazine.service;

import com.geonoo.magazine.dto.UsersDto;
import com.geonoo.magazine.exception.MsgEnum;
import com.geonoo.magazine.model.Role;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.UsersRepository;
import com.geonoo.magazine.security.JwtTokenProvider;
import com.geonoo.magazine.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;

    @Transactional
    public String saveUser(UsersDto usersDto){
        Users users = Users.builder()
                .email(usersDto.getEmail())
                .password(passwordEncoder.encode(usersDto.getPassword()))
                .nick(usersDto.getUsername())
                .roles(Collections.singletonList(Role.USER.getName()))
                .build();

        if (checkEmailDuple(users.getEmail())){
            throw new IllegalArgumentException(MsgEnum.duplicateEmail.getMsg());
        }else{
            usersRepository.save(users);
            return MsgEnum.registrationComplete.getMsg();
        }
    }
    public boolean checkEmailDuple(String email){
        return usersRepository.existsByEmail(email);
    }

    public Map<String, String> login(UsersDto usersDto) {
        Map<String, String> token = new HashMap<>();

        Users users = usersRepository.findByEmail(usersDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(MsgEnum.confirmEmailPwd.getMsg()));

        if (!passwordEncoder.matches(usersDto.getPassword(), users.getPassword())) {
            throw new IllegalArgumentException(MsgEnum.confirmEmailPwd.getMsg());
        }
        token.put(MsgEnum.jwtHeaderName.getMsg(), jwtTokenProvider.createAccessToken(users));
        return token;
    }
    @Transactional
    public String deleteUser(UserDetailsImpl userDetails) {
        Users users = usersRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException(MsgEnum.userNotFound.getMsg())
        );
        usersRepository.deleteById(users.getUser_id());
        return MsgEnum.deleteComplete.getMsg();
    }
}
