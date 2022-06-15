package com.geonoo.magazine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "f-id")
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long user_id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Boards boards;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;

    public Favorites(Boards boards, Users users) {
        this.user_id = users.getUser_id();
        users.addFavorites(this);
        boards.addFavorites(this);
    }
}
