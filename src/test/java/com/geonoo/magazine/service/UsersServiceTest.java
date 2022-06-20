package com.geonoo.magazine.service;


import com.geonoo.magazine.dto.UsersDto;
import com.geonoo.magazine.exception.MsgEnum;
import com.geonoo.magazine.model.Role;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.UsersRepository;
import com.geonoo.magazine.security.JwtTokenProvider;
import com.geonoo.magazine.security.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static com.geonoo.magazine.exception.MsgEnum.jwtHeaderName;
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

        //when
        when(usersRepository.existsByEmail(usersDto.getEmail())).thenReturn(true);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                usersService.saveUser(usersDto)
        );
        // then
        assertEquals(MsgEnum.duplicateEmail.getMsg(), exception.getMessage());
    }

    @Test
    @DisplayName("비밀번호 확인 정상")
    void test3() {
        //given
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

    @Test
    @DisplayName("정상 로그인")
    void test5() {
        //given
        List<String> role = new ArrayList<>();
        role.add("ROLE_USER");
        Users users = Users.builder()
                .user_id(1L)
                .email("adad@naver.com")
                .password(passwordEncoder.encode("Passw0rd!@#"))
                .roles(role)
                .nick("건우짱")
                .build();

        Map<String, String> token = new HashMap<>();
        token.put(jwtHeaderName.getMsg(), "토큰토큰");

        UsersDto usersDto = UsersDto.builder()
                .email("adad@naver.com")
                .password("Passw0rd!@#")
                .build();

        //when
        when(usersRepository.findByEmail("adad@naver.com")).thenReturn(Optional.of(users));
        when(passwordEncoder.matches(usersDto.getPassword(), users.getPassword())).thenReturn(true);
        when(jwtTokenProvider.createAccessToken(users)).thenReturn("토큰토큰");

        Map<String, String> result = usersService.login(usersDto);

        //then
        assertEquals("토큰토큰", result.get(MsgEnum.jwtHeaderName.getMsg()));
    }

    @Test
    @DisplayName("로그인 실패 - 없는 이메일")
    void test6() {
        //given
        String email = "geonoo@naver.com";
        String password = "Passw0rd!@#";
        UsersDto usersDto = UsersDto.builder()
                .email(email)
                .password(password)
                .build();


        //when
        when(usersRepository.findByEmail(email)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                usersService.login(usersDto)
        );

        //then
        assertEquals(MsgEnum.confirmEmailPwd.getMsg(), exception.getMessage());
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 틀림")
    void test7() {
        //given
        String email = "geonoo@naver.com";
        String password = "Passw0rd!@#";

        UsersDto usersDto = UsersDto.builder()
                .email(email)
                .password(password)
                .build();

        Users users = Users.builder()
                .email(email)
                .password("인코딩된 비밀번호")
                .build();


        //when
        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(users));
        when(passwordEncoder.matches(usersDto.getPassword(), users.getPassword())).thenReturn(false);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                usersService.login(usersDto)
        );

        //then
        assertEquals(MsgEnum.confirmEmailPwd.getMsg(), exception.getMessage());
    }

    @Test
    @DisplayName("정상 회원삭제")
    void test8() {
        //given
        String email = "geonoo@naver.com";
        UserDetailsImpl userDetails = new UserDetailsImpl(email, null);
        Users users = Users.builder()
                .user_id(1L)
                .email("geonoo@naver.com")
                .build();
        //when
        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(users));
        String result = usersService.deleteUser(userDetails);

        //then
        assertEquals(MsgEnum.deleteComplete.getMsg(), result);
    }

    @Test
    @DisplayName("회원삭제 - 실패")
    void test9() {
        //given
        String email = "geonoo@naver.com";
        UserDetailsImpl userDetails = new UserDetailsImpl(email, null);
        //when
        when(usersRepository.findByEmail(email)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                usersService.deleteUser(userDetails)
        );

        //then
        assertEquals(MsgEnum.userNotFound.getMsg(), exception.getMessage());
    }
}