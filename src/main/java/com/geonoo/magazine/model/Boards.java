package com.geonoo.magazine.model;

import com.geonoo.magazine.dto.BoardsDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class Boards extends BaseTime{


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false) //Null 이면 안됌
    private Long userId;
    private String title;
    private String body;

    private Long viewCount;

    public void update(BoardsDto boardsDto){
        this.title = boardsDto.getTitle();
        this.body = boardsDto.getBody();
    }

}
