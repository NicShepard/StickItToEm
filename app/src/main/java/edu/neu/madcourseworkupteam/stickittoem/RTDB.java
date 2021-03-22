package edu.neu.madcourseworkupteam.stickittoem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//TODO: Do these things need to happen in the background and not on the main thread?
// WHAT DOES THIS MEAN: Receiving sticker works in foreground and background
//Note: push method generates unique key
public class RTDB {
    private DatabaseReference database;
    private User user;
    private String token;


    public RTDB (String userName) {
        database = FirebaseDatabase.getInstance().getReference().child("users");
        user = new User(userName);
        token = "";

    }

    public String getToken() {
        //TODO: Get the user token from the database and set it
        return token;
    }

    /**
     *
     * @param userName
     */
    private void updateTokenForUser(String userName) {
        //TODO: figure out how to get device key
    }

    private String getTokenForUser(String receiver) {
        //TODO: parse dictionary JSON object
        return "";
    }


    private void sendEmojitoUser(String receiverId, String emojiID) {


    }

    private ArrayList getSentEmojies(String token, String userID) {
//        HashMap hm = new HashMap();
//        return hm;
        ArrayList<String> list = new ArrayList<>();
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User model = dataSnapshot.getValue(User.class);
                    //String tempstring = model.score;
                    //list.add(tempstring);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        return list;

        }

    private HashMap getRecievedEmojies(String token, String userID) {
        //TODO: do we need this or can it be done with the function above
        HashMap hm = new HashMap();
        return hm;
    }


}


//
//  database.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                User model = dataSnapshot.getValue(User.class);
//                String tempstring = model.score.toString();
//                list.add(tempstring);
//            }
//        }
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//    }