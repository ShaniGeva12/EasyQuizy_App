package com.example.easyquizy_app.Model;

public class Question {

    private String Question, AnswerA, AnswerB, AnswerC, AnswerD, CorrectAnswer, CategoryId, IsImageQuestion;
    private int AnswerAi;
    private int AnswerBi;
    private int AnswerCi;
    private int AnswerDi;
    int CorrectAnswerInt;

    public Question() {
    }

    public Question(String question, String answerA, String answerB, String answerC, String answerD, String correctAnswer, String categoryId, String isImageQuestion) {
        Question = question;
        AnswerA = answerA;
        AnswerB = answerB;
        AnswerC = answerC;
        AnswerD = answerD;
        CorrectAnswer = correctAnswer;
        this.CategoryId = categoryId;
        this.IsImageQuestion = isImageQuestion;
    }

    public Question(String question, int answerA, int answerB, int answerC, int answerD, int correctAnswer) {
        Question = question;
        AnswerAi = answerA;
        AnswerBi = answerB;
        AnswerCi = answerC;
        AnswerDi = answerD;
        CorrectAnswerInt = correctAnswer;
    }


    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public void setAnswerA(String answerA) {
        AnswerA = answerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public void setAnswerB(String answerB) {
        AnswerB = answerB;
    }

    public String getAnswerC() {
        return AnswerC;
    }

    public void setAnswerC(String answerC) {
        AnswerC = answerC;
    }

    public String getAnswerD() {
        return AnswerD;
    }

    public void setAnswerD(String answerD) {
        AnswerD = answerD;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public int getCorrectAnswerIntAns() {
        return CorrectAnswerInt;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        this.CategoryId = categoryId;
    }

    public String getIsImageQuestion() {
        return IsImageQuestion;
    }

    public void setIsImageQuestion(String isImageQuestion) {
        this.IsImageQuestion = isImageQuestion;
    }

    public int getAnswerAi() {
        return AnswerAi;
    }

    public void setAnswerAi(int answerAi) {
        AnswerAi = answerAi;
    }

    public int getAnswerBi() {
        return AnswerBi;
    }

    public void setAnswerBi(int answerBi) {
        AnswerBi = answerBi;
    }

    public int getAnswerCi() {
        return AnswerCi;
    }

    public void setAnswerCi(int answerCi) {
        AnswerCi = answerCi;
    }

    public int getAnswerDi() {
        return AnswerDi;
    }

    public void setAnswerDi(int answerDi) {
        AnswerDi = answerDi;
    }


    public enum QuestionType {
        american,
        fillWord,
        freeTxt
    }
}
