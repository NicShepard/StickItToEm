package edu.neu.madcourseworkupteam.stickittoem;

import java.util.ArrayList;

import java.util.HashMap;

public class User {

    public String username;
    public String deviceToken;

    public HashMap<String, String> sent;
    public HashMap<String, String> received;

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


        this.sentStickers = new ArrayList();
        this.recievedStickers = new ArrayList();
    }

    public void addReceivedStickers(String stickerID) {
        recievedStickers.add(stickerID);
    }

    public void addSentStickers(String stickerID) {
        sentStickers.add(stickerID);

    }

}

