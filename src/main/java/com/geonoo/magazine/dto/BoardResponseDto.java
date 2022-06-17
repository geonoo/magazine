package com.geonoo.magazine.dto;

import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.model.Favorites;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardResponseDto {
    private LocalDateTime createdDate;
    private Long board_id;
    private String title;
    private String body;
    private String img_url;
    private Long viewCount;
    private int template;
    private String username;
    private List<Favorites> favoritesList;

    public BoardResponseDto(Boards boards){
        this.createdDate = boards.getCreatedDate();
        this.board_id = boards.getBoard_id();
        this.title = boards.getTitle();
        this.body = boards.getBody();
        this.img_url = boards.getImg_url();
        this.viewCount = boards.getViewCount();
        this.template = boards.getTemplate();
        this.username = boards.getUsername();
        this.favoritesList = boards.getFavoritesList();
    }
}
