package com.geonoo.magazine.service;

import com.geonoo.magazine.dto.BoardsDto;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.BoardsRepository;
import com.geonoo.magazine.repository.UsersRepository;
import com.geonoo.magazine.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardsService {

    private final BoardsRepository boardsRepository;

    private final UsersRepository usersRepository;

    public String saveBoard(BoardsDto boardsDto, UserDetailsImpl userDetails){

        Users users = usersRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 아닙니다")
        );
        Boards boards = new Boards(
                boardsDto.getTitle(),
                boardsDto.getImg_url(),
                boardsDto.getBody(),
                boardsDto.getTemplate(),
                users);

        boardsRepository.save(boards);
        return "추가완료";
    }

    public List<Boards> findAllBoard(){
        return boardsRepository.findAll();
    }

    @Transactional
    public Boards findByBoardId(Long boardId){
        boardsRepository.updateView(boardId);
        Boards boards = getBoards(boardId);
        return boards;
    }

    @Transactional
    public String deleteOneBoard(Long boardId){
        getBoards(boardId);
        boardsRepository.deleteById(boardId);
        return "삭제완료";
    }

    @Transactional
    public String updateOneBoard(Long boardId, BoardsDto boardsDto){
        Boards boards = getBoards(boardId);
        boards.update(boardsDto);
        return "수정완료";
    }

    private Boards getBoards(Long boardId) {
        Boards boards = boardsRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시물이 없습니다")
        );
        return boards;
    }
}
