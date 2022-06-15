package com.geonoo.magazine.controller;

import com.geonoo.magazine.dto.BoardsDto;
import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.s3.AwsS3Service;
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

    private final AwsS3Service awsS3Service;

    @PostMapping("/api/board")
    public String addBoard(@Valid @RequestBody BoardsDto boardsDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        loginCheck(userDetails);
        return boardsService.saveBoard(boardsDto, userDetails);
    }

    /**
     * Amazon S3에 파일 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */
    @PostMapping("/api/board/file")
    public List<String> uploadFile(@RequestPart List<MultipartFile> multipartFile) {
        return awsS3Service.uploadFile(multipartFile);
    }

    /**
     * Amazon S3에 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */
    @DeleteMapping("/api/board/file")
    public String deleteFile(@RequestBody String img_url) {
        awsS3Service.deleteFile(img_url);
        return "ok";
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
