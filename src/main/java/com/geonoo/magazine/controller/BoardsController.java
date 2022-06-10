package com.geonoo.magazine.controller;

import com.geonoo.magazine.dto.BoardsDto;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.service.BoardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardsController {

    private final BoardsService boardsService;

    @PostMapping("/api/board")
    public Boards addBoard(@Valid @RequestBody BoardsDto boardsDto) {
        return boardsService.save(boardsDto);
    }

    @GetMapping("/api/board")
    public List<Boards> listBoard(){
        return boardsService.findAll();
    }

    @GetMapping("/api/board/{boardId}")
    public Boards oneBoard(@PathVariable Long boardId){
        return boardsService.findByBoardId(boardId);
    }

    @DeleteMapping("/api/board/{boardId}")
    public Long deleteBoard(@PathVariable Long boardId){
        return boardsService.deleteOne(boardId);
    }

    @PutMapping("/api/board/{boardId}")
    public Boards putBoard(@Valid @PathVariable Long boardId, @RequestBody BoardsDto boardsDto){
        return boardsService.updateOne(boardId, boardsDto);
    }

}
