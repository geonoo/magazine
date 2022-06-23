package com.geonoo.magazine.service;

import com.geonoo.magazine.exception.MsgEnum;
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
            throw new IllegalArgumentException(MsgEnum.alreadyLiked.getMsg());
        }

        Favorites likes = Favorites.builder()
                .boards(boards)
                .users(users)
                .build();
        favoritesRepository.save(likes);
        return favoritesRepository.countAllByBoardsAndUsers(boards, users);
    }

    public int unlikes(Long boardId, UserDetailsImpl userDetails){

        Users users = getUsers(userDetails);
        Boards boards = getBoards(boardId);

        Optional<Favorites> optionalFavorites = favoritesRepository.findByBoardsAndUsers(boards, users);
        if(optionalFavorites.isEmpty()){
            throw new IllegalArgumentException(MsgEnum.alreadyNotLiked.getMsg());
        }

        favoritesRepository.deleteById(optionalFavorites.get().getId());
        return favoritesRepository.countAllByBoardsAndUsers(boards, users);
    }

    private Users getUsers(UserDetailsImpl userDetails) {
        return usersRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException(MsgEnum.userNotFound.getMsg())
        );
    }

    private Boards getBoards(Long boardId) {
        return boardsRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException(MsgEnum.boardNotFound.getMsg())
        );
    }

}
