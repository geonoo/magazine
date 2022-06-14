package com.geonoo.magazine.repository;

import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.model.Favorites;
import com.geonoo.magazine.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    Optional<Favorites> findByBoardsAndUsers(Boards boards, Users users);

    int countAllByBoardsAndUsers(Boards boards, Users users);
}
