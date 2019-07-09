package com.example.easyquizy_app.Model;

public class Ranking {

    private String userName;
    private long score;

    public Ranking() { }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public Ranking(String userName, long score) {
        this.userName = userName;
        this.score = score;
    }
}
