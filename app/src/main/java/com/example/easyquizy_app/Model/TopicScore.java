package com.example.easyquizy_app.Model;

public class TopicScore {

    private int score;
    private String topicName;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public TopicScore(int score, String topicName) {
        this.score = score;
        this.topicName = topicName;
    }
    public TopicScore() {
    }
}
