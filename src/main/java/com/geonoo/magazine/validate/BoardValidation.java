package com.geonoo.magazine.validate;

import com.geonoo.magazine.dto.BoardsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BoardValidation {
    public static void checkEmpty(BoardsDto boardsDto){
        boolean nullCheck = (boardsDto.getTitle() == null || boardsDto.getBody() == null);
        if (nullCheck || boardsDto.getTitle().isEmpty() || boardsDto.getBody().isEmpty()){
            throw new IllegalArgumentException("제목 또는 내용을 입력해주세요");
        }
    }

    public static void checkMultiPart(List<MultipartFile> list){
        if (list == null || list.size() == 0){
            throw new IllegalArgumentException("파일을 등록해주세요");
        }
    }
}
