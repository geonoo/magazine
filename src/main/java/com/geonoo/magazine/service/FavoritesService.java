package com.geonoo.magazine.service;

import com.geonoo.magazine.model.Boards;
import com.geonoo.magazine.model.Favorites;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.BoardsRepository;
import com.geonoo.magazine.repository.FavoritesRepository;
import com.geonoo.magazine.repository.UsersRepository;
import com.geonoo.magazine.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    final FavoritesRepository favoritesRepository;
    final UsersRepository usersRepository;
    final BoardsRepository boardsRepository;

    public int likes(Long boardId, UserDetailsImpl userDetails){

        Users users = getUsers(userDetails);
        Boards boards = getBoards(boardId);

        Optional<Favorites> optionalLikes = favoritesRepository.findByBoardsAndUsers(boards, users);
        if(optionalLikes.isPresent()){
            throw new IllegalArgumentException("이미 좋아요 누른 사용자 입니다");
        }

        Favorites likes = new Favorites(boards, users);
        favoritesRepository.save(likes);
        return favoritesRepository.countAllByBoardsAndUsers(boards, users);
    }

    public int unlikes(Long boardId, UserDetailsImpl userDetails){

        Users users = getUsers(userDetails);
        Boards boards = getBoards(boardId);

        Optional<Favorites> optionalFavorites = favoritesRepository.findByBoardsAndUsers(boards, users);
        if(!optionalFavorites.isPresent()){
            throw new IllegalArgumentException("취소할 내용이 없습니다");
        }

        favoritesRepository.deleteById(optionalFavorites.get().getId());
        return favoritesRepository.countAllByBoardsAndUsers(boards, users);
    }

    private Users getUsers(UserDetailsImpl userDetails) {
        return usersRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다")
        );
    }

    private Boards getBoards(Long boardId) {
        return boardsRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시물이 없습니다")
        );
    }

}
