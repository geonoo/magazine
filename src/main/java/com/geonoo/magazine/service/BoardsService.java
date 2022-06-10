package com.geonoo.magazine.service;

import com.geonoo.magazine.dto.BoardsDto;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.repository.BoardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BoardsService {

    private final BoardsRepository boardsRepository;

    @Autowired
    BoardsService(BoardsRepository boardsRepository){
        this.boardsRepository = boardsRepository;
    }


    @Transactional
    public Boards save(BoardsDto boardsDto){
        Boards boards = Boards
                .builder()
                .userId(boardsDto.getUserId())
                .title(boardsDto.getTitle())
                .body(boardsDto.getBody())
                .viewCount(0L)
                .build();
        return boardsRepository.save(boards);
    }

    @Transactional
    public List<Boards> findAll(){
        return boardsRepository.findAll();
    }

    @Transactional
    public Boards findByBoardId(Long boardId){
        boardsRepository.updateView(boardId);
        Boards boards = getBoards(boardId);
        return boards;
    }

    @Transactional
    public Long deleteOne(Long boardId){
        getBoards(boardId);
        boardsRepository.deleteById(boardId);
        return boardId;
    }

    @Transactional
    public Boards updateOne(Long boardId, BoardsDto boardsDto){
        Boards boards = getBoards(boardId);
        boards.update(boardsDto);
        return boards;
    }

    private Boards getBoards(Long boardId) {
        Boards boards = boardsRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시물이 없습니다")
        );
        return boards;
    }
}
