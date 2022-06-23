package com.geonoo.magazine.validate;

import com.geonoo.magazine.security.UserDetailsImpl;

public class UserValidation {
    public static void loginCheck(UserDetailsImpl userDetails) {
        if(userDetails == null){
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
    }
}
