package com.geonoo.magazine.service;

import com.geonoo.magazine.dto.BoardResponseDto;
import com.geonoo.magazine.dto.BoardsDto;
import com.geonoo.magazine.exception.MsgEnum;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.BoardsRepository;
import com.geonoo.magazine.repository.UsersRepository;
import com.geonoo.magazine.security.UserDetailsImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardsService {

    private final BoardsRepository boardsRepository;

    private final UsersRepository usersRepository;

    private final AwsS3Service awsS3Service;

    @Value("${cloud.aws.domain}")
    private final String domain;

    public String saveBoard(BoardsDto boardsDto, UserDetailsImpl userDetails , List<MultipartFile> files){

        Users users = usersRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException(MsgEnum.userNotFound.getMsg())
        );
        Boards boards = Boards.builder()
                .title(boardsDto.getTitle())
                .img_url(awsS3Service.uploadFile(files).get(0))
                .body(boardsDto.getBody())
                .template(boardsDto.getTemplate())
                .user(users)
                .build();

        boardsRepository.save(boards);
        return MsgEnum.addComplete.getMsg();
    }

    public Page<BoardResponseDto> findAllBoard(Pageable pageable){
        Page<Boards> all = boardsRepository.findAll(pageable);
        System.out.println(all.getSize());
        return boardsRepository.findAll(pageable).map(BoardResponseDto::new);
    }

    public List<Boards> findByUsers(String email){
        Users users = usersRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException(MsgEnum.userNotFound.getMsg())
        );
        return boardsRepository.findByUsers(users);
    }

    @Transactional
    public Boards findByBoardId(Long boardId){
        boardsRepository.updateView(boardId);
        return getBoards(boardId);
    }

    @Transactional
    public String deleteOneBoard(Long boardId){
        Boards boards = getBoards(boardId);
        log.info(boards.getImg_url().split(domain)[1]);
        awsS3Service.deleteFile(boards.getImg_url().split(domain)[1]);
        boardsRepository.deleteById(boardId);
        return MsgEnum.deleteComplete.getMsg();
    }

    @Transactional
    public String updateOneBoard(Long boardId, BoardsDto boardsDto, List<MultipartFile> files){
        Boards boards = getBoards(boardId);
        String imgUrl = boards.getImg_url();
        if (files != null && files.size() > 0){
            imgUrl = awsS3Service.uploadFile(files).get(0);
            awsS3Service.deleteFile(boards.getImg_url().split(domain)[1]);
        }
        String title = boards.getTitle();
        String body = boards.getBody();
        int tempate = boards.getTemplate();

        if (boardsDto.getTitle() != null && !boardsDto.getTitle().isEmpty()) title = boardsDto.getTitle();

        if (boardsDto.getBody() != null && !boardsDto.getBody().isEmpty()) body = boardsDto.getBody();

        if (boardsDto.getTemplate() != 0 && boardsDto.getTemplate() != boards.getTemplate()) tempate = boardsDto.getTemplate();


        BoardsDto updateDto = BoardsDto.builder()
                .title(title)
                .body(body)
                .template(tempate)
                .img_url(imgUrl)
                .build();
        boards.update(updateDto);

        return MsgEnum.updateComplete.getMsg();
    }

    private Boards getBoards(Long boardId) {
        return boardsRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException(MsgEnum.boardNotFound.getMsg())
        );
    }
}
