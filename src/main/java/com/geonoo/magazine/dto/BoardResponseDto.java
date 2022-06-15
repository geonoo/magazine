package com.geonoo.magazine.dto;

import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.model.Favorites;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BoardResponseDto {
    private Long boardId;
    private String title;
    private String body;
    private String img_url;
    private Long viewCount;
    private int template;
    private List<Favorites> favoritesList = new ArrayList<>();
    private int likeCount;

    public BoardResponseDto(Boards boards){
        this.boardId = boards.getBoard_id();
        this.title = boards.getTitle();
        this.body = boards.getBody();
        this.img_url = boards.getImg_url();
        this.viewCount = boards.getViewCount();
        this.template = boards.getTemplate();
        this.favoritesList = boards.getFavoritesList();
        this.likeCount = boards.getFavoritesList().size();
    }
}
