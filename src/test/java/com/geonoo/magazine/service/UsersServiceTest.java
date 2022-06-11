package com.geonoo.magazine.service;

import com.geonoo.magazine.dto.UsersDto;
import com.geonoo.magazine.repository.BoardsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {
    @Mock
    BoardsRepository boardsRepository;

    @InjectMocks //Mock 주입
    BoardsService boardsService;

    @Test
    @DisplayName("회원가입")
    void saveUser() {
        String email = "123";
        String password = "123";
        String username = "123";

        UsersDto usersDto = new UsersDto(
                email,
                password,
                username
        );
    }

}