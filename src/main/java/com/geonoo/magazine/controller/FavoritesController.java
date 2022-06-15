package com.geonoo.magazine.controller;

import com.geonoo.magazine.security.UserDetailsImpl;
import com.geonoo.magazine.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping("/api/board/test")
    public void testBoard(HttpServletRequest request){
        log.info(request.getRemoteAddr());
    }

    private void loginCheck(UserDetailsImpl userDetails) {
        if(userDetails == null){
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
    }

}
