package com.geonoo.magazine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geonoo.magazine.dto.BoardsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "b-id")
public class Boards extends BaseTime{


    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long board_id;
    @Column(length=500)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;
    private String img_url;
    private Long viewCount = 0L;
    private int template;

    private String username;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private Users users;


    //어떤문제가 있을까?
//    @JsonIgnore
//    @JsonManagedReference
    @OneToMany(mappedBy = "boards", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Favorites> favoritesList = new ArrayList<>();

    public void addFavorites(Favorites favorites){
        favorites.setBoards(this);
        this.favoritesList.add(favorites);
    }
    public void setUsers(Users users){
        this.users = users;
    }


    @Builder
    public Boards(Long board_id, String title, String img_url, String body, int template, Users user) {
        this.board_id = board_id;
        this.title = title;
        this.img_url = img_url;
        this.body = body;
        this.viewCount = 0L;
        this.template = template;
        this.username = user.getNick();
        user.addBoards(this);
    }

    public Boards() {

    }

    public void update(BoardsDto boardsDto){
        this.title = boardsDto.getTitle();
        this.body = boardsDto.getBody();
        this.img_url = boardsDto.getImg_url();
        this.template = boardsDto.getTemplate();
    }

}
