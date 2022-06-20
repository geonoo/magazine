package com.geonoo.magazine.service;

import com.geonoo.magazine.dto.BoardResponseDto;
import com.geonoo.magazine.dto.BoardsDto;
import com.geonoo.magazine.exception.MsgEnum;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.BoardsRepository;
import com.geonoo.magazine.repository.UsersRepository;
import com.geonoo.magazine.security.UserDetailsImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardsServiceTest {
    @Mock
    BoardsRepository boardsRepository;
    @Mock
    UsersRepository usersRepository;
    @Mock
    AwsS3Service awsS3Service;
    @Mock
    Pageable pageableMock;
    @InjectMocks
    BoardsService boardsService;
    private final String domain = "https://gwoo.aws.com/";

    private List<MultipartFile> getImage() throws IOException {
        File file = new File("src/test/java/com/geonoo/magazine/img/" +"밥.png");
        FileItem fileItem = new DiskFileItem(
                "밥.png",
                Files.probeContentType(file.toPath()),
                false,
                file.getName(),
                (int) file.length(),
                file.getParentFile()
        );
        IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        List<MultipartFile> fileList = new ArrayList<>();
        fileList.add(multipartFile);
        return fileList;
    }

    @DisplayName("정상 게시물 등록")
    @Test
    void test1() throws IOException {
        //given
        UserDetailsImpl userDetails = new UserDetailsImpl("geonoo@naver.com", null);

        Users users = Users.builder()
                .user_id(1L)
                .email("geonoo@naver.com")
                .build();

        BoardsDto boardsDto = BoardsDto.builder()
                .title("나는 제목 꿈을 꾸는 제목")
                .body("나는 내용 꿈을 꾸는 내용")
                .template(2)
                .build();

        List<MultipartFile> files = getImage();

        List<String> fileRtn = new ArrayList<>();
        fileRtn.add(domain+"밥.png");
        //when
        when(usersRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(users));
        when(awsS3Service.uploadFile(files)).thenReturn(fileRtn);

        String result = boardsService.saveBoard(boardsDto, userDetails, files);

        //then
        assertEquals(MsgEnum.addComplete.getMsg(), result);
    }

    @DisplayName("실패 게시물 등록 - 등록된 사용자가 아님")
    @Test
    void test2() throws IOException {
        //given
        UserDetailsImpl userDetails = new UserDetailsImpl("geonoo@naver.com", null);

        BoardsDto boardsDto = BoardsDto.builder()
                .title("나는 제목 꿈을 꾸는 제목")
                .body("나는 내용 꿈을 꾸는 내용")
                .template(2)
                .build();

        List<MultipartFile> files = getImage();

        List<String> fileRtn = new ArrayList<>();
        fileRtn.add(domain+"밥.png");
        //when
        when(usersRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                boardsService.saveBoard(boardsDto, userDetails, files)
        );
        //then
        assertEquals(MsgEnum.userNotFound.getMsg(), exception.getMessage());
    }

    @DisplayName("전체 게시물 조회(페이징)")
    @Test
    void test3() {
        //given
        Users users = Users.builder()
                .user_id(1L)
                .email("geonoo@naver.com")
                .nick("별명")
                .build();
        Boards boards = Boards.builder()
                .title("나는 제목 꿈을 꾸는 제목")
                .body("나는 내용 꿈을 꾸는 내용")
                .template(2)
                .img_url("http://aaa.dadadadada.png")
                .user(users)
                .build();
        List<Boards> boardsList = new ArrayList<>();
        boardsList.add(boards);
        Page<Boards> page = new PageImpl<>(boardsList);

        //when
        when(boardsRepository.findAll(pageableMock)).thenReturn(page);
        Page<BoardResponseDto> result = boardsService.findAllBoard(pageableMock);
        //then
        assertEquals(1, result.getSize());
        assertEquals(boards.getTitle(), result.getContent().get(0).getTitle());
    }

    @DisplayName("유저별 등록한 게시물")
    @Test
    void test4() {
        //given
        Users users = Users.builder()
                .user_id(1L)
                .email("geonoo@naver.com")
                .nick("별명")
                .build();
        Boards boards = Boards.builder()
                .title("나는 제목 꿈을 꾸는 제목")
                .body("나는 내용 꿈을 꾸는 내용")
                .template(2)
                .img_url("http://aaa.dadadadada.png")
                .user(users)
                .build();
        List<Boards> boardsList = new ArrayList<>();
        boardsList.add(boards);

        //when
        when(usersRepository.findByEmail(any())).thenReturn(Optional.of(users));
        when(boardsRepository.findByUsers(users)).thenReturn(boardsList);
        List<Boards> result = boardsService.findByUsers(users.getEmail());
        //then
        assertEquals(boards.getTitle(), result.get(0).getTitle());
    }

    @DisplayName("유저별 등록한 게시물 - 등록되지 않은 유저")
    @Test
    void test5() {
        //given
        Users users = Users.builder()
                .user_id(1L)
                .email("geonoo@naver.com")
                .nick("별명")
                .build();
        //when
        when(usersRepository.findByEmail(any())).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                boardsService.findByUsers(users.getEmail())
        );
        //then
        assertEquals(MsgEnum.userNotFound.getMsg(), exception.getMessage());
    }

    @DisplayName("게시물 한개 조회")
    @Test
    void test6() {
        //given
        Users users = Users.builder()
                .user_id(1L)
                .email("geonoo@naver.com")
                .nick("별명")
                .build();
        Boards boards = Boards.builder()
                .board_id(2L)
                .title("나는 제목 꿈을 꾸는 제목")
                .body("나는 내용 꿈을 꾸는 내용")
                .template(2)
                .img_url(domain+"휴식.png")
                .user(users)
                .build();
        //when
        when(boardsRepository.updateView(any())).thenReturn(1);
        when(boardsRepository.findById(any())).thenReturn(Optional.of(boards));
        Boards result = boardsService.findByBoardId(2L);
        //then
        assertEquals(boards.getTitle(), result.getTitle());
    }

    @DisplayName("게시물 한개 조회 - 실패 게시물 없을 때")
    @Test
    void test7() {
        //given
        //when
        when(boardsRepository.updateView(any())).thenReturn(1);
        when(boardsRepository.findById(any())).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                boardsService.findByBoardId(2L)
        );
        //then
        assertEquals(MsgEnum.boardNotFound.getMsg(), exception.getMessage());
    }

    @DisplayName("게시물 삭제")
    @Test
    void test8() {
        //given
        Users users = Users.builder()
                .user_id(1L)
                .email("geonoo@naver.com")
                .nick("별명")
                .build();
        Boards boards = Boards.builder()
                .board_id(2L)
                .title("나는 제목 꿈을 꾸는 제목")
                .body("나는 내용 꿈을 꾸는 내용")
                .template(2)
                .img_url(domain+"휴식.png")
                .user(users)
                .build();
        //when
        BoardsService boardsServiceRe = new BoardsService(boardsRepository, usersRepository, awsS3Service, domain);
        when(boardsRepository.findById(any())).thenReturn(Optional.of(boards));
        String result = boardsServiceRe.deleteOneBoard(2L);
        //then
        assertEquals(MsgEnum.deleteComplete.getMsg(),result);
    }

    @DisplayName("정상 게시물 수정")
    @Test
    void test9() throws IOException {
        //given
        Users users = Users.builder()
                .user_id(1L)
                .email("geonoo@naver.com")
                .build();

        BoardsDto boardsDto = BoardsDto.builder()
                .title("나는 제목 꿈을 꾸는 제목")
                .body("나는 내용 꿈을 꾸는 내용")
                .template(2)
                .build();

        Boards boards = Boards.builder()
                .board_id(2L)
                .title("원본 제목")
                .body("원본 내용")
                .template(1)
                .img_url(domain+"원래.png")
                .user(users)
                .build();

        List<MultipartFile> files = getImage();

        List<String> fileRtn = new ArrayList<>();
        fileRtn.add(domain+"밥.png");
        //when
        BoardsService boardsServiceRe = new BoardsService(boardsRepository, usersRepository, awsS3Service, domain);
        when(boardsRepository.findById(any())).thenReturn(Optional.of(boards));
        when(awsS3Service.uploadFile(files)).thenReturn(fileRtn);

        String result = boardsServiceRe.updateOneBoard(2L, boardsDto, files);

        //then
        assertEquals(MsgEnum.updateComplete.getMsg(), result);
    }
}