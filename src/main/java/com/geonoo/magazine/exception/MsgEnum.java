package com.geonoo.magazine.exception;

public enum MsgEnum {

    registrationComplete("회원가입완료"),
    duplicateEmail("이미 사용중인 이메일 입니다"),
    confirmEmailPwd("이메일 또는 패스워드를 확인해주세요"),
    userNotFound("사용자를 찾을 수 없습니다"),
    deleteComplete("삭제완료"),
    updateComplete("수정완료"),
    jwtHeaderName("Access-Token"),
    jwtHeaderNameRefresh("Refresh-Token"),
    boardNotFound("게시물을 찾을 수 없습니다"),
    alreadyLiked("이미 좋아요 누른 사용자 입니다"),
    alreadyNotLiked("좋아요 먼저 하셔야 취소가 가능합니다"),
    fileUploadFail("파일 업로드에 실패했습니다."),
    addComplete("추가완료");

    final private String msg;
    public String getMsg() {
        return msg;
    }
    private MsgEnum(String msg){
        this.msg = msg;
    }
}
