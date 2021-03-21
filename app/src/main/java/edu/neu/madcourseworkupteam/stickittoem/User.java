package edu.neu.madcourseworkupteam.stickittoem;

import java.util.ArrayList;

public class User {

    public String username;
    public String score;
    public String deviceToken;

    public User(){
        // Default constructor
    }

    public User(String username){
        this.username = username;
    }

    public User(String username, String score){
        this.username = username;
        this.score = score;
        this.deviceToken = "";
    }

    public User(String username, String score, String token) {
        this.username = username;
        this.score = score;
        this.deviceToken = token;
    }
}
