package com.geonoo.magazine.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geonoo.magazine.dto.BoardsDto;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.security.UserDetailsImpl;
import com.geonoo.magazine.service.BoardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardsController {

    private final BoardsService boardsService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/api/board")
    public String addBoard(@RequestParam("board") String board, @RequestPart List<MultipartFile> files
            , @AuthenticationPrincipal UserDetailsImpl userDetails) throws JsonProcessingException {
        loginCheck(userDetails);
        BoardsDto boardsDto = objectMapper.readValue(board, BoardsDto.class);
        return boardsService.saveBoard(boardsDto, userDetails, files);
    }

    @GetMapping("/api/board")
    public List<Boards>  listBoard(){
//        List<BoardResponseDto> resultList = Arrays.asList(
//                modelMapper.map(boardsService.findAllBoard(), BoardResponseDto[].class)
//        );
        return boardsService.findAllBoard();
    }

    @GetMapping("/api/board/{boardId}")
    public Boards oneBoard(@PathVariable Long boardId){
        return boardsService.findByBoardId(boardId);
    }

    @GetMapping("/api/board/user")
    public List<Boards> userBoard(@AuthenticationPrincipal UserDetailsImpl userDetails){
        loginCheck(userDetails);
        return boardsService.findByUsers(userDetails.getUsername());
    }


    @DeleteMapping("/api/board/{boardId}")
    public String deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        loginCheck(userDetails);
        return boardsService.deleteOneBoard(boardId);
    }

    @PutMapping("/api/board/{boardId}")
    public String putBoard(@Valid @PathVariable Long boardId, @RequestParam("board") String board
            , @RequestPart List<MultipartFile> files, @AuthenticationPrincipal UserDetailsImpl userDetails) throws JsonProcessingException {
        loginCheck(userDetails);
        BoardsDto boardsDto = objectMapper.readValue(board, BoardsDto.class);
        return boardsService.updateOneBoard(boardId, boardsDto, files);
    }

    private void loginCheck(UserDetailsImpl userDetails) {
        System.out.println(userDetails);
        if(userDetails == null){
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
    }

}
