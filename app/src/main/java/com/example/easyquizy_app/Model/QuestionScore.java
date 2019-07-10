package com.example.easyquizy_app.Model;

public class QuestionScore {
    private String Question_Score;
    private String User;
    private int Score;
    private String CategoryName;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public QuestionScore() {
    }

    public QuestionScore(String question_Score, String user, int score,String CategoryName) {
        Question_Score = question_Score;
        User = user;
        Score = score;
        CategoryName = CategoryName;
    }

    public String getQuestion_Score() {
        return Question_Score;
    }

    public void setQuestion_Score(String question_Score) {
        Question_Score = question_Score;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
