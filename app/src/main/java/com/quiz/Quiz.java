package com.quiz;

public class Quiz {
    String name;
    boolean is_ad;
    String icon_url;
    int quiz_id;

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean is_ad() {
        return is_ad;
    }

    public void set_is_ad(boolean is_Ad) {
        this.is_ad = is_ad;
    }
}
