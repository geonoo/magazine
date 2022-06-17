package com.geonoo.magazine.service;

import com.geonoo.magazine.dto.BoardsDto;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.BoardsRepository;
import com.geonoo.magazine.repository.UsersRepository;
import com.geonoo.magazine.s3.AwsS3Service;
import com.geonoo.magazine.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardsService {

    private final BoardsRepository boardsRepository;

    private final UsersRepository usersRepository;

    private final AwsS3Service awsS3Service;

    @Value("${cloud.aws.domain}")
    private String domain;

    public String saveBoard(BoardsDto boardsDto, UserDetailsImpl userDetails , List<MultipartFile> files){

        Users users = usersRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 아닙니다")
        );
        Boards boards = new Boards(
                boardsDto.getTitle(),
                awsS3Service.uploadFile(files).get(0),
                boardsDto.getBody(),
                boardsDto.getTemplate(),
                users);

        boardsRepository.save(boards);
        return "추가완료";
    }

    public List<Boards> findAllBoard(){
        return boardsRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
    }

    public List<Boards> findByUsers(String email){
        Users users = usersRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 아닙니다")
        );
        return boardsRepository.findByUsers(users);
    }

    @Transactional
    public Boards findByBoardId(Long boardId){
        boardsRepository.updateView(boardId);
        Boards boards = getBoards(boardId);
        return boards;
    }

    @Transactional
    public String deleteOneBoard(Long boardId){
        Boards boards = getBoards(boardId);
        log.info(boards.getImg_url().split(domain)[1]);
        awsS3Service.deleteFile(boards.getImg_url().split(domain)[1]);
        boardsRepository.deleteById(boardId);
        return "삭제완료";
    }

    @Transactional
    public String updateOneBoard(Long boardId, BoardsDto boardsDto, List<MultipartFile> files){
        Boards boards = getBoards(boardId);
        awsS3Service.deleteFile(boards.getImg_url().split(domain)[1]);
        boardsDto.setImg_url(awsS3Service.uploadFile(files).get(0));
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
