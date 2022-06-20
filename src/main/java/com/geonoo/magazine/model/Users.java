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
@Getter
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
    private List<String> roles = new ArrayList<>();

    @Builder
    public Users(Long user_id, String email, String nick, String password, List<String> roles){
        this.user_id = user_id;
        this.email = email;
        this.nick = nick;
        this.password = password;
        this.roles = roles;
    }

    public Users() {

    }

}
