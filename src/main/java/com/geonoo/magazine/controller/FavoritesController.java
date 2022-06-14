package com.geonoo.magazine.controller;

import com.geonoo.magazine.security.UserDetailsImpl;
import com.geonoo.magazine.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FavoritesController {
    final FavoritesService favoritesService;

    @PostMapping("/api/board/{boardId}/like")
    public Map<String, String> addLike(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Map<String, String> map = new HashMap<>();
        loginCheck(userDetails);
        map.put("likeCount", String.valueOf(favoritesService.likes(boardId, userDetails)));
        return map;
    }

    @DeleteMapping("/api/board/{boardId}/like")
    public Map<String, String> deleteLike(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Map<String, String> map = new HashMap<>();
        loginCheck(userDetails);
        map.put("likeCount", String.valueOf(favoritesService.unlikes(boardId, userDetails)));
        return map;
    }

    private void loginCheck(UserDetailsImpl userDetails) {
        if(userDetails == null){
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
    }

}
