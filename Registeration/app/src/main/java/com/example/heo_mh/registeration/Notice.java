package com.example.heo_mh.registeration;

/**
 * Created by Heo-MH on 2017-02-21.
 * 공지 사항
 */

public class Notice {

    String notice;  // 내용
    String name;    // 작성자
    String date;    // 작성 일자

    public Notice(String notice, String name, String date) {
        this.notice = notice;
        this.name = name;
        this.date = date;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
