//package com.geonoo.magazine.model;
//
//import com.geonoo.magazine.dto.BoardsDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//@Nested
//@DisplayName("게시물 객체")
//class BoardsTest {
//    @Nested
//    @DisplayName("게시물 객체 생성 테스트")
//    class CreateBoardsTest{
//        @BeforeEach
//        void setup() {
//            userId = 100L;
//            title = "나는 제목 꿈을 꾸는 제목";
//            body = "나는 내용 꿈을 꾸는 내용";
//            viewCount = 10L;
//        }
//        private Long userId;
//        private String title;
//        private String body;
//        private Long viewCount;
//
//        @Test
//        @DisplayName("정상 케이스")
//        void createBoards_Normal() {
//            //given
//            BoardsDto boardsDto = new BoardsDto(
//                    userId,
//                    title,
//                    body
//            );
//            //when
//            Boards boards = Boards
//                    .builder()
//                    .userId(boardsDto.getUserId())
//                    .title(boardsDto.getTitle())
//                    .body(boardsDto.getBody())
//                    .build();
//            //than
//            assertNull(boards.getBoardId());
//            assertEquals(userId, boards.getUserId());
//            assertEquals(title, boards.getTitle());
//            assertEquals(body, boards.getBody());
//        }
//
////        @Test
////        @DisplayName("실패 케이스")
////        void createBoards_fail() {
////
////        }
//
//    }
//
//
//
//
//
//
//}