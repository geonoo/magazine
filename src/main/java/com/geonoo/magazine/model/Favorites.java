package com.geonoo.magazine.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //어떤문제가 있을까?
    //    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Boards boards;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;

    public Favorites(Boards boards, Users users) {
        users.addFavorites(this);
        boards.addFavorites(this);
    }

    public Favorites() {

    }
}
