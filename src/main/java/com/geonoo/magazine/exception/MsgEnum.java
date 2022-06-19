package com.geonoo.magazine.exception;

public enum MsgEnum {

    registrationComplete("회원가입완료"),
    duplicateEmail("이미 사용중인 이메일 입니다"),
    confirmEmailPwd("이메일 또는 패스워드를 확인해주세요"),
    userNotFound("사용자를 찾을 수 없습니다."),
    deleteComplete("삭제완료"),
    jwtHeaderName("Access-Token"),
    jwtHeaderNameRefresh("Refresh-Token");

    final private String msg;
    public String getMsg() {
        return msg;
    }
    private MsgEnum(String msg){
        this.msg = msg;
    }
}
