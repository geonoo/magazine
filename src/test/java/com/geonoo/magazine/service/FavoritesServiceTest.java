package com.geonoo.magazine.service;

import com.geonoo.magazine.exception.MsgEnum;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.model.Favorites;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.BoardsRepository;
import com.geonoo.magazine.repository.FavoritesRepository;
import com.geonoo.magazine.repository.UsersRepository;
import com.geonoo.magazine.security.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FavoritesServiceTest {
    @Mock
    FavoritesRepository favoritesRepository;
    @Mock
    UsersRepository usersRepository;
    @Mock
    BoardsRepository boardsRepository;
    @InjectMocks
    FavoritesService favoritesService;

    @DisplayName("정상 좋아요")
    @Test
    void test1(){
        //given
        UserDetailsImpl userDetails = new UserDetailsImpl("geonoo@naver.com", null);
        Users users = Users.builder()
                .user_id(1L)
                .email(userDetails.getUsername())
                .build();
        Boards boards = Boards.builder()
                .board_id(2L)
                .title("나는 제목")
                .body("나는 내용")
                .user(users)
                .build();

        //when
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.of(users));
        when(boardsRepository.findById(any())).thenReturn(Optional.of(boards));
        when(favoritesRepository.findByBoardsAndUsers(boards, users)).thenReturn(Optional.empty());
        when(favoritesRepository.countAllByBoardsAndUsers(boards, users)).thenReturn(1);

        int count = favoritesService.likes(boards.getBoard_id(), userDetails);
        //then
        assertEquals(1, count);
    }

    @DisplayName("실패 좋아요 - 이미 좋아요 했을 때")
    @Test
    void test2(){
        //given
        UserDetailsImpl userDetails = new UserDetailsImpl("geonoo@naver.com", null);
        Users users = Users.builder()
                .user_id(1L)
                .email(userDetails.getUsername())
                .build();
        Boards boards = Boards.builder()
                .board_id(2L)
                .title("나는 제목")
                .body("나는 내용")
                .user(users)
                .build();

        Favorites favorites = Favorites.builder()
                .boards(boards)
                .users(users)
                .build();
        //when
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.of(users));
        when(boardsRepository.findById(any())).thenReturn(Optional.of(boards));
        when(favoritesRepository.findByBoardsAndUsers(boards, users)).thenReturn(Optional.of(favorites));
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                favoritesService.likes(boards.getBoard_id(), userDetails)
        );
        //then
        assertEquals(MsgEnum.alreadyLiked.getMsg(), exception.getMessage());
    }

    @DisplayName("실패 좋아요 - 사용자 없을 때")
    @Test
    void test3(){
        //given
        UserDetailsImpl userDetails = new UserDetailsImpl("geonoo@naver.com", null);
        Users users = Users.builder()
                .user_id(1L)
                .email(userDetails.getUsername())
                .build();
        Boards boards = Boards.builder()
                .board_id(2L)
                .title("나는 제목")
                .body("나는 내용")
                .user(users)
                .build();
        //when
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                favoritesService.likes(boards.getBoard_id(), userDetails)
        );
        //then
        assertEquals(MsgEnum.userNotFound.getMsg(), exception.getMessage());

    }

    @DisplayName("실패 좋아요 - 게시물 없을 때")
    @Test
    void test4(){
        //given
        UserDetailsImpl userDetails = new UserDetailsImpl("geonoo@naver.com", null);
        Users users = Users.builder()
                .user_id(1L)
                .email(userDetails.getUsername())
                .build();
        Boards boards = Boards.builder()
                .board_id(2L)
                .title("나는 제목")
                .body("나는 내용")
                .user(users)
                .build();
        //when
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.of(users));
        when(boardsRepository.findById(any())).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                favoritesService.likes(boards.getBoard_id(), userDetails)
        );
        //then
        assertEquals(MsgEnum.boardNotFound.getMsg(), exception.getMessage());
    }

    @DisplayName("정상 좋아요 취소")
    @Test
    void test5(){
        //given
        UserDetailsImpl userDetails = new UserDetailsImpl("geonoo@naver.com", null);
        Users users = Users.builder()
                .user_id(1L)
                .email(userDetails.getUsername())
                .build();
        Boards boards = Boards.builder()
                .board_id(2L)
                .title("나는 제목")
                .body("나는 내용")
                .user(users)
                .build();

        Favorites favorites = Favorites.builder()
                .boards(boards)
                .users(users)
                .build();

        //when
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.of(users));
        when(boardsRepository.findById(any())).thenReturn(Optional.of(boards));
        when(favoritesRepository.findByBoardsAndUsers(boards, users)).thenReturn(Optional.of(favorites));
        when(favoritesRepository.countAllByBoardsAndUsers(boards, users)).thenReturn(0);
        int count = favoritesService.unlikes(boards.getBoard_id(), userDetails);
        //then
        assertEquals(0, count);
    }

    @DisplayName("실패 좋아요 취소 - 취소할 좋아요가 없을 때")
    @Test
    void test6(){
        //given
        UserDetailsImpl userDetails = new UserDetailsImpl("geonoo@naver.com", null);
        Users users = Users.builder()
                .user_id(1L)
                .email(userDetails.getUsername())
                .build();
        Boards boards = Boards.builder()
                .board_id(2L)
                .title("나는 제목")
                .body("나는 내용")
                .user(users)
                .build();

        //when
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.of(users));
        when(boardsRepository.findById(any())).thenReturn(Optional.of(boards));
        when(favoritesRepository.findByBoardsAndUsers(boards, users)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                favoritesService.unlikes(boards.getBoard_id(), userDetails)
        );
        //then
        assertEquals(MsgEnum.alreadyNotLiked.getMsg(), exception.getMessage());
    }

}