package com.geonoo.magazine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Builder
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "u-id")
public class Users extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nick;

    @JsonIgnore
    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Boards> boardsList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Favorites> favoritesList = new ArrayList<>();

    public void addFavorites(Favorites favorites){
        favorites.setUsers(this);
        this.favoritesList.add(favorites);
    }

    public void addBoards(Boards boards){
        this.boardsList.add(boards);
        boards.setUsers(this);
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

}
