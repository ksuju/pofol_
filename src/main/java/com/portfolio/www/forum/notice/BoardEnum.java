package com.portfolio.www.forum.notice;

public enum BoardEnum {
	HOME("0","홈"),
	NOTICE("1","공지사항"),
	INQUIRY("2","고객센터");

	
	BoardEnum(String code, String description){
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
