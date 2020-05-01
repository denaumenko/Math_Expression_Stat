package com.example.login.domain;


import javax.persistence.*;
import java.util.Date;

@Entity
public class TimeTable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User person;

    private String event;
    private Date date;

    private int corect;
    private int incorect;
    private int questions;

    private int max;
    private int min;
    private double avg;




    public TimeTable(String event, Date date, User person, int corect, int incorect,int questions, int max, int min, double avg) {
        this.event = event;
        this.date = date;
        this.person = person;
        this.corect = corect;
        this.incorect = incorect;
        this.questions = questions;
        this.max = max;
        this.min = min;
        this.avg = avg;
    }


    public TimeTable(){

    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "id=" + id +
                ", person=" + person +
                ", event='" + event + '\'' +
                ", date=" + date +
                ", corect=" + corect +
                ", incorect=" + incorect +
                ", questions=" + questions +
                ", max=" + max +
                ", min=" + min +
                ", avg=" + avg +
                '}';
    }

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public int getCorect() {
        return corect;
    }

    public void setCorect(int corect) {
        this.corect = corect;
    }

    public int getIncorect() {
        return incorect;
    }

    public void setIncorect(int incorect) {
        this.incorect = incorect;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
