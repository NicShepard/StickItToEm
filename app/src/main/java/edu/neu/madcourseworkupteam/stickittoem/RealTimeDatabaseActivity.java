package edu.neu.madcourseworkupteam.stickittoem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is where the user sends emojis to a single friend. The architecture, and our design, supports sending
 * notifications to many different friends, but due to time constraints and question @200 on Piazza.
 * We decided to make it just one friend while leaving the elements to show the work that was done.
 */
public class RealTimeDatabaseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RTDB ACTIVITY";

    private String currentUser;
    private DatabaseReference database;
    private TextView userName;
    private EditText sendToFriend;
    private String otherUser = "otherUser";
    private ImageView smileEmoji;
    private ImageView sadEmoji;
    private ImageView laughEmoji;
    private ImageView angryEmoji;

    /**
     * Find all the views by their ID, get the device token for the user, and save it under their
     * userID in RTDB. For the purposes of this assignment, we have foregone a synthetic primary key
     * and made the design decision that users are not able to change their username.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_database);

        userName = (TextView) findViewById(R.id.username);

        database = FirebaseDatabase.getInstance().getReference();

        smileEmoji = (ImageView) findViewById(R.id.smiley);
        sadEmoji = (ImageView) findViewById(R.id.sad);
        laughEmoji = (ImageView) findViewById(R.id.laughing);
        angryEmoji = (ImageView) findViewById(R.id.angry);
        sendToFriend = (EditText) findViewById(R.id.sendToUser);

        smileEmoji.setOnClickListener(this::onClick);
        sadEmoji.setOnClickListener(this::onClick);
        laughEmoji.setOnClickListener(this::onClick);
        angryEmoji.setOnClickListener(this::onClick);

        //We originally made this adjustable, but we'll hardcode it for now.
        sendToFriend.setText("otherUser");

        String token = "";
        try {
            //Get current user's token
            token = FirebaseInstanceId.getInstance().getToken();
            Log.w("RECEIVED Token: ", token);

            //Get current user's name and set it
            currentUser = getIntent().getStringExtra("CURRENT_USER");
            userName.setText(currentUser);
            Log.w("CURRENT USER: ", currentUser);

            //Set the token as a child for the current user so other users can eventually look them
            // up and ping them in another iteration of the app
            database.child("users").child(currentUser).child("deviceToken").setValue(token);

        } catch (Exception e) {
            Log.d("Failed to complete token refresh", String.valueOf(e));
        }


        database.child("users").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.e(TAG, "onChildAdded: dataSnapshot = " + dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.v(TAG, "onChildChanged: " + dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled:" + databaseError);
                    }
                }
        );
    }

    /**
     * Define the onClick method that all the emoji buttons will use. It calls onSendEmoji
     * and onReceiveEmoji which populate entries or users of all the messages they sent, all the
     * messages they received, and the timestamp of the message (to be used later as a composite
     * primary key)
     */
    @Override
    public void onClick(View view) {

        //Get a timestamp to use to identify each message
        String timestamp = String.valueOf(new Date().getTime());

        switch (view.getId()) {
            case R.id.received:
                Intent intent = new Intent(getApplicationContext(), ReceivedActivity.class);
                intent.putExtra("CURRENT_USER", userName.getText().toString());
                startActivity(intent);
                break;
            // emoji 1

            case R.id.smiley:
                // TODO: figure out how to get current user id
                RealTimeDatabaseActivity.this.onSendEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "smiley", timestamp);
                RealTimeDatabaseActivity.this.onReceiveEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "smiley", timestamp);
                break;
            // emoji 2
            case R.id.laughing:
                // TODO: figure out how to get current user id
                RealTimeDatabaseActivity.this.onSendEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "laughing", timestamp);
                RealTimeDatabaseActivity.this.onReceiveEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "laughing", timestamp);
                break;
            // emoji 3
            case R.id.sad:
                // TODO: figure out how to get current user id
                RealTimeDatabaseActivity.this.onSendEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "sad", timestamp);
                RealTimeDatabaseActivity.this.onReceiveEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "sad", timestamp);
                break;
            // emoji 4
            case R.id.angry:
                // TODO: figure out how to get current user id
                RealTimeDatabaseActivity.this.onSendEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "angry", timestamp);
                RealTimeDatabaseActivity.this.onReceiveEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "angry", timestamp);
                break;
        }
    }


    private void onSendEmoji(DatabaseReference database, String currentUser, String otherUser, String emoji, String timestamp) {

        String token = getToken(database, otherUser);
        database
                .child("users")
                .child(currentUser)
                .child("sent")
                .child(otherUser)
                .child(timestamp)
                .setValue(emoji);
    }

    private void onReceiveEmoji(DatabaseReference database, String currentUser, String otherUser, String emoji, String timestamp) {

        database
                .child("users")
                .child(otherUser)
                .child("received")
                .child(currentUser)
                .child(timestamp)
                .setValue(emoji);
    }

    /**
     * Gets the device token
     */
    public String getToken(DatabaseReference database, String user) {

        final String[] value = new String[1];
        value[0] = null;

        Query query = database.child("users").child(user).orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("TOKEN FROM FB", snapshot.getKey());
                    if (snapshot.getKey().equalsIgnoreCase("deviceToken")) {
                        value[0] = snapshot.getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error while reading data");
            }
        });

        return value[0];
    }

    /**
     * Get the emojis for a user
     */
    public List<String> getEmojisForUser(DatabaseReference database, String user) {

        List emojiList = new LinkedList();

        database.child("users").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.e("SNAPSHOT IS", snapshot.getKey());
                    if (snapshot.getKey().equalsIgnoreCase("received")) {
                        for (DataSnapshot receivedMessageUser : snapshot.getChildren()) {
                            if (snapshot.getKey() != null) {
                                for (DataSnapshot message : receivedMessageUser.getChildren()) {
                                    Log.e("ADDING", message.getValue().toString());
                                    emojiList.add(message.getValue().toString());
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error while reading data");
            }
        });

        return emojiList;
    }


}