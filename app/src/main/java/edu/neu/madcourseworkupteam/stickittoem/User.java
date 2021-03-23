package edu.neu.madcourseworkupteam.stickittoem;

import java.util.ArrayList;

public class User {

    public String username;
    public String deviceToken;
    public String sent;
    public String received;
    public ArrayList sentStickers;
    public ArrayList recievedStickers;

    public User(){
        // Default constructor
    }

    public User(String username){
        this.username = username;
        this.sentStickers = new ArrayList();
        this.recievedStickers = new ArrayList();
    }

    public User(String username, String token) {
        this.username = username;
        this.deviceToken = token;
        this.sent = "";
        this.received = "";
        this.sentStickers = new ArrayList();
        this.recievedStickers = new ArrayList();
    }

    public void sendEmoji(String username, String emoji){
        this.sent += username + ":" + emoji + ";";
    }

    public void receiveEmoji(String username, String emoji){
        this.received += username + ":" + emoji + ";";
    }

    public void addReceivedStickers(String stickerID) {
        recievedStickers.add(stickerID);

    }

    public void addSentStickers(String stickerID) {
        sentStickers.add(stickerID);

    }
}

