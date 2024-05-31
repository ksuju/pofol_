package com.portfolio.www.message;

public enum MessageEnum {

    //생성자 무조건 필요, 앞에 부분을 생성자로 취급
    SUCCESS("0000","회원가입성공"),
    FAILED("0001","회원가입실패"),
    USER_NOT_FOUND("9000","사용자가 없습니다"),
	VALLID_USER_NAME("1100","유효하지 않은 아이디."),
	VALLID_PASSWD("1101","유효하지 않은 패스워드."),
	EQUAL_PASSWD("2000","비밀번호가 일치합니다."),
	NOT_EQUAL_PASSWD("2001","비밀번호가 일치하지 않습니다."),
	DUPL_ID("1103","중복된 아이디 입니다."),
	NO_DUPL_ID("1104","사용 가능한 아이디 입니다.");

    MessageEnum(String code, String description){
        this.code = code;
        this.description = description;
    }

    private String code;
    private String description;

    //Enum은 setter 필요X, getter만 필요
    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }
}