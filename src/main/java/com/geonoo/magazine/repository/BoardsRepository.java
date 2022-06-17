package com.geonoo.magazine.repository;

import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardsRepository extends JpaRepository<Boards, Long> {

    @Modifying
    @Query("update Boards p set p.viewCount = p.viewCount + 1 where p.board_id = :id")
    int updateView(Long id);


//    @Query("select b from Boards b where b.users.user_id" =: userId)
    List<Boards> findByUsers(Users users);

}
