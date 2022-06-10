package com.geonoo.magazine.service;

import com.geonoo.magazine.dto.BoardsDto;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.repository.BoardsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardsServiceTest {

    @Mock
    BoardsRepository boardsRepository;

    @InjectMocks //Mock 주입
    BoardsService boardsService;

    @Test
    @DisplayName("게시물 추가")
    void save() {
        //given
        Long userId = 100L;
        String title = "나는 제목 꿈을 꾸는 제목";
        String body ="나는 내용 꿈을 꾸는 내용";
        BoardsDto boardsDto = new BoardsDto(
                userId,
                title,
                body
        );
        Boards boards = Boards
                .builder()
                .userId(boardsDto.getUserId())
                .title(boardsDto.getTitle())
                .body(boardsDto.getBody())
                .build();

        //when
        when(boardsRepository.save(any(Boards.class))).thenReturn(boards);
        Boards result = boardsService.save(boardsDto);
        //then
        assertEquals(userId, result.getUserId());
        assertEquals(title, result.getTitle());
        assertEquals(body, result.getBody());
    }

    @Test
    @DisplayName("게시물 전체 찾기")
    void listBoard(){
        //given
        Long userId = 100L;
        String title = "나는 제목 꿈을 꾸는 제목";
        String body ="나는 내용 꿈을 꾸는 내용";
        Boards boards = Boards.builder()
                .userId(userId)
                .title(title)
                .body(body)
                .build();
        //when
        List<Boards> boardsList = new ArrayList<>();
        boardsList.add(boards);

        //then
        when(boardsRepository.findAll()).thenReturn(boardsList);
        List<Boards> resultList = boardsService.findAll();

        assertEquals(1, resultList.size());
        assertEquals(boards.getTitle(), resultList.get(0).getTitle());
    }

    @Test
    @DisplayName("게시물 한개 찾기")
    void oneBoard() {
        //given
        Long boardId = 10L;
        Long userId = 100L;
        Long view_count = 11L;
        String title = "나는 제목 꿈을 꾸는 제목";
        String body ="나는 내용 꿈을 꾸는 내용";
        Boards boards = Boards.builder()
                .boardId(boardId)
                .userId(userId)
                .title(title)
                .body(body)
                .viewCount(view_count)
                .build();

        //when
        when(boardsRepository.findById(boardId))
                .thenReturn(Optional.ofNullable(boards));
        Boards result = boardsService.findByBoardId(boardId);

        //then
        assertEquals(boardId, result.getBoardId());
        assertEquals(userId, result.getUserId());
        assertEquals(title, result.getTitle());
        assertEquals(body, result.getBody());
        assertEquals(11L, result.getViewCount());
    }

    @Test
    @DisplayName("게시물 한개 삭제")
    void deleteBoard() {
        //given
        Long boardId = 10L;
        Long userId = 100L;
        Long view_count = 11L;
        String title = "나는 제목 꿈을 꾸는 제목";
        String body ="나는 내용 꿈을 꾸는 내용";
        Boards boards = Boards.builder()
                .boardId(boardId)
                .userId(userId)
                .title(title)
                .body(body)
                .viewCount(view_count)
                .build();

        //when
        when(boardsRepository.findById(boardId))
                .thenReturn(Optional.ofNullable(boards));
        Long result = boardsService.deleteOne(boardId);

        //then
        assertEquals(boardId, result);
    }

    @Test
    @DisplayName("게시물 한개 수정")
    void updateBoard() {
        //given
        Long boardId = 10L;
        Long userId = 100L;
        String title = "나는 제목 꿈을 꾸는 제목";
        String body ="나는 내용 꿈을 꾸는 내용";
        BoardsDto boardsDto = new BoardsDto(
                userId,
                title,
                body
        );
        Boards boards = Boards
                .builder()
                .userId(boardsDto.getUserId())
                .title("수정 전 제목")
                .body("수정 전 내용")
                .build();

        when(boardsRepository.findById(boardId))
                .thenReturn(Optional.ofNullable(boards));
        Boards result = boardsService.updateOne(boardId, boardsDto);

        assertEquals(title, result.getTitle());
        assertEquals(body, result.getBody());
    }
}

