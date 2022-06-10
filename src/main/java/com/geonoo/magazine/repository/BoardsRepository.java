package com.geonoo.magazine.repository;

import com.geonoo.magazine.model.Boards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardsRepository extends JpaRepository<Boards, Long> {

    @Modifying
    @Query("update Boards p set p.viewCount = p.viewCount + 1 where p.boardId = :id")
    int updateView(Long id);

}
