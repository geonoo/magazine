package com.geonoo.magazine.integration;


import com.geonoo.magazine.dto.BoardsDto;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.service.BoardsService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardsIntegrationTest {
    @Autowired
    BoardsService boardsService;

    Long userId = 3L;

    Boards createdBoards;

    @Test
    @Order(1)
    @DisplayName("게시물 등록")
    void test1() {
        // given
        String title = "나는 제목 꿈을 꾸는 제목";
        String body = "나는 내용 꿈을 꾸는 내용";
        BoardsDto boardsDto = new BoardsDto(
                userId,
                title,
                body
        );
        // when
        Boards boards = boardsService.saveBoard(boardsDto);
        // then
        assertNotNull(boards.getBoardId());
        assertEquals(userId, boards.getUserId());
        assertEquals(title, boards.getTitle());
        assertEquals(body, boards.getBody());
        assertEquals(0L, boards.getViewCount());
        createdBoards = boards;
    }

    @Test
    @Order(2)
    @DisplayName("게시물 전체 찾기")
    void test2() {
        // given

        //when
        List<Boards> boardsList = boardsService.findAllBoard();

        //then
        assertEquals(1, boardsList.size());
        assertEquals(createdBoards.getTitle(), boardsList.get(0).getTitle());
    }

    @Test
    @Order(3)
    @DisplayName("게시물 한개 찾기")
    void test3() {
        //given
        Long boardId = createdBoards.getBoardId();
        //when
        Boards boards = boardsService.findByBoardId(boardId);
        //then
        assertEquals(createdBoards.getBoardId(), boards.getBoardId());
        assertEquals(createdBoards.getTitle(), boards.getTitle());
        assertEquals(createdBoards.getBody(), boards.getBody());
    }

    @Test
    @Order(4)
    @DisplayName("게시물 한개 수정")
    void test4() {
        //given
        String title = "나는 제목 꿈을 꾸는 제목 수정함";
        String body = "나는 내용 꿈을 꾸는 내용 수정함";
        BoardsDto boardsDto = new BoardsDto(
                null,
                title,
                body
        );
        //when
        Boards boards = boardsService.updateOneBoard(createdBoards.getBoardId(), boardsDto);
        //then
        assertEquals(createdBoards.getBoardId(), boards.getBoardId());
        assertEquals(createdBoards.getUserId(), boards.getUserId());
        assertEquals(body, boards.getBody());
        assertEquals(title, boards.getTitle());
    }

    @Test
    @Order(5)
    @DisplayName("게시물 삭제")
    void  test5(){
        //given
        Long boardId = createdBoards.getBoardId();
        //when
        Long resultId = boardsService.deleteOneBoard(boardId);
        //then
        assertEquals(boardId, resultId);
    }

}
