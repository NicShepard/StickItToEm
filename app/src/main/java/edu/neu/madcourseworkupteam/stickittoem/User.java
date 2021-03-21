package edu.neu.madcourseworkupteam.stickittoem;

public class User {

    public String username;
    public String score;
    public String deviceToken;
    public String sent;
    public String received;

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
        this.sent = "";
        this.received = "";
    }

    public User(String username, String score, String token) {
        this.username = username;
        this.score = score;
        this.deviceToken = token;
        this.sent = "";
        this.received = "";
    }

    public void sendEmoji(String username, String emoji){
        this.sent += username + ":" + emoji + ";";
    }

    public void receiveEmoji(String username, String emoji){
        this.received += username + ":" + emoji + ";";
    }
}

