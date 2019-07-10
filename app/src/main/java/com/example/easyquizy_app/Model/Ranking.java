package com.example.easyquizy_app.Model;

public class Ranking {

    private String userName;
    private String score;

    public Ranking() { }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Ranking(String userName, String score) {
        this.userName = userName;
        this.score = score;
    }
}
