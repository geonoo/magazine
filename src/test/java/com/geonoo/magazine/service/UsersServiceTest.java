package com.geonoo.magazine.service;


import com.geonoo.magazine.dto.UsersDto;
import com.geonoo.magazine.exception.MsgEnum;
import com.geonoo.magazine.model.Role;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.UsersRepository;
import com.geonoo.magazine.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    private UsersService usersService;

    @Test
    @DisplayName("정상 회원 가입")
    void test1() {
        // given
        UsersDto usersDto = UsersDto.builder()
                .email("ada@navaer.com")
                .password("1234qwas@@")
                .username("건우짱")
                .build();

        Users users = Users.builder()
                .email(usersDto.getEmail())
                .password(usersDto.getPassword())
                .nick(usersDto.getUsername())
                .roles(Collections.singletonList(Role.USER.getName()))
                .build();

        //when
        Mockito.lenient().when(usersService.saveUser(usersDto)).thenReturn(MsgEnum.registrationComplete.getMsg());
        when(usersRepository.findByEmail("ada@navaer.com")).thenReturn(Optional.of(users));

        String result = usersService.saveUser(usersDto);
        Optional<Users> rtnUser = usersRepository.findByEmail(usersDto.getEmail());

        // then
        assertEquals(MsgEnum.registrationComplete.getMsg(), result);
        assertEquals("ada@navaer.com", rtnUser.get().getEmail());
    }

    @Test
    @DisplayName("회원 가입-중복확인")
    void test2() {
        // given
        UsersDto usersDto = UsersDto.builder()
                .email("ada@navaer.com")
                .build();

        UsersDto usersDto2 = UsersDto.builder()
                .email("ada@navaer.com")
                .build();

        //when
        when(usersService.checkEmailDuple(usersDto.getEmail())).thenReturn(false);

        // then
        boolean result = usersService.checkEmailDuple(usersDto2.getEmail());
        assertEquals(false, result);

    }

    void exception1(String email){
        if (usersService.checkEmailDuple(email)){
            throw new IllegalArgumentException(MsgEnum.duplicateEmail.getMsg());
        }
    }

    @Test
    @DisplayName("비밀번호 확인 정상")
    void test3() {
        //given
        Map<String, String> token = new HashMap<>();
        List<String> role = new ArrayList<>();

        UsersDto usersDto = UsersDto.builder()
                .email("ada@navaer.com")
                .password("123@#qweQA")
                .build();

        role.add(Role.USER.getName());
        Users users = Users.builder()
                .user_id(1L)
                .nick("hello")
                .password(passwordEncoder.encode(usersDto.getPassword()))
                .roles(role)
                .build();



        //when
        when(passwordEncoder.matches(usersDto.getPassword(), users.getPassword())).thenReturn(true);
        boolean result = passwordEncoder.matches(usersDto.getPassword(), users.getPassword());

        //then
        assertTrue(result);

    }

    @Test
    @DisplayName("비밀번호 틀렸을 때")
    void test4() {
        //given
        Map<String, String> token = new HashMap<>();
        List<String> role = new ArrayList<>();

        UsersDto usersDto = UsersDto.builder()
                .email("ada@navaer.com")
                .password("123@#qweQA")
                .build();

        role.add(Role.USER.getName());
        Users users = Users.builder()
                .user_id(1L)
                .nick("hello")
                .password(passwordEncoder.encode(usersDto.getPassword()))
                .roles(role)
                .build();



        //when
        when(passwordEncoder.matches("1234", users.getPassword())).thenReturn(false);
        boolean result = passwordEncoder.matches("1234", users.getPassword());

        //then
        assertFalse(result);

    }

}