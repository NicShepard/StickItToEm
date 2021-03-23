package edu.neu.madcourseworkupteam.stickittoem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    public String username;
    public String deviceToken;
    public HashMap<String, String> sent;
    public HashMap<String, String> received;

    public User(){
        // Default constructor
    }

    public User(String username){
        this.username = username;
    }

    public User(String username, String token) {
        this.username = username;
        this.deviceToken = token;
    }

}

