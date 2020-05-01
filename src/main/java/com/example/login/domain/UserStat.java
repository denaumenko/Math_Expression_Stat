package com.example.login.domain;

import javax.persistence.*;

@Entity
public class UserStat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    private String expression;
    private int answer;
    private int solution;

    private boolean accuracy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User person;

    public UserStat(String expression, int answer, int solution, boolean accuracy, User person) {
        this.expression = expression;
        this.answer = answer;
        this.accuracy = accuracy;
        this.person = person;
        this.solution = solution;
    }

    public int getSolution() {
        return solution;
    }

    public void setSolution(int solution) {
        this.solution = solution;
    }

    public UserStat() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public boolean isAccuracy() {
        return accuracy;
    }

    public void setAccuracy(boolean accuracy) {
        this.accuracy = accuracy;
    }

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return person+ ", " + expression + ", " + answer + ", ";
    }
}
