package com.geonoo.magazine.controller;

import com.geonoo.magazine.dto.BoardResponseDto;
import com.geonoo.magazine.dto.BoardsDto;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.security.UserDetailsImpl;
import com.geonoo.magazine.service.BoardsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class BoardsController {

    private final BoardsService boardsService;
    private final ModelMapper modelMapper;

    @PostMapping("/api/board")
    public String addBoard(@Valid @RequestBody BoardsDto boardsDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        loginCheck(userDetails);
        return boardsService.saveBoard(boardsDto, userDetails);
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

    @DeleteMapping("/api/board/{boardId}")
    public String deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        loginCheck(userDetails);
        return boardsService.deleteOneBoard(boardId);
    }

    @PutMapping("/api/board/{boardId}")
    public String putBoard(@Valid @PathVariable Long boardId, @RequestBody BoardsDto boardsDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        loginCheck(userDetails);
        return boardsService.updateOneBoard(boardId, boardsDto);
    }

    private void loginCheck(UserDetailsImpl userDetails) {
        if(userDetails == null){
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
    }

}
