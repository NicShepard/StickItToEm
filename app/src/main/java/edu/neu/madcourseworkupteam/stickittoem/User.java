package edu.neu.madcourseworkupteam.stickittoem;

public class User {

    public String username;
    public String score;
    public String datePlayed;

    public User(){
        // Default constructor
    }

    public User(String username){
        this.username = username;
    }

    public User(String username, String score){
        this.username = username;
        this.score = score;
        this.datePlayed = "2021-03-19";
    }

    public User(String username, String score, String date) {
        this.username = username;
        this.score = score;
        this.datePlayed = date;
    }
}
