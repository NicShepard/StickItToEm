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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Date;

//TODO: Figure out how to get username from login
// when click login button, should take to this activity
// and should have access to the username the user entered

/**
 *
 */
public class RealTimeDatabaseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RealTimeDatabaseActivity.class.getSimpleName();

    private DatabaseReference database;
    private TextView userName;
    private EditText sendToFriend;
    private ImageView smileEmoji;
    private ImageView sadEmoji;
    private ImageView laughEmoji;
    private ImageView angryEmoji;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_database);

        userName = (TextView) findViewById(R.id.username);
        sendToFriend = (EditText) findViewById(R.id.sendToUser);

        database = FirebaseDatabase.getInstance().getReference();

        smileEmoji = (ImageView) findViewById(R.id.smiley);
        sadEmoji = (ImageView) findViewById(R.id.sad);
        laughEmoji = (ImageView) findViewById(R.id.plus);
        angryEmoji = (ImageView) findViewById(R.id.lock);

        smileEmoji.setOnClickListener(this::onClick);
        sadEmoji.setOnClickListener(this::onClick);
        laughEmoji.setOnClickListener(this::onClick);
        angryEmoji.setOnClickListener(this::onClick);

        // Either add new user if the username entered is not in the database
        // or update device token if different than the one stored with the
        // username in the database
        // or do nothing
        String token = "";
        try {
            token = FirebaseInstanceId.getInstance().getToken();
            Log.w("RECEIVED Token: ", token);
            Log.w("CURRENT USER: ", getIntent().getStringExtra("CURRENT_USER"));
            userName.setText(getIntent().getStringExtra("CURRENT_USER"));
            User user = new User(userName.getText().toString(), token);
            database.child("users").child(user.username).child("deviceToken").setValue(user);

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

    @Override
    public void onClick(View view) {

        String timestamp = String.valueOf(new Date().getTime());

        switch (view.getId()) {
            case R.id.received:
                startActivity(new Intent(getApplicationContext(), ReceivedActivity.class));
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

    /**
     * @param postRef
     * @param currentUser
     */
    private void onSendEmoji(DatabaseReference postRef, String currentUser, String otherUser, String emoji, String timestamp) {
        postRef
                .child("users")
                .child(currentUser)
                .child("sent")
                .child(otherUser)
                .child(timestamp)
                .setValue(emoji);
    }

    /**
     * @param postRef
     * @param currentUser
     */
    private void onReceiveEmoji(DatabaseReference postRef, String currentUser, String otherUser, String emoji, String timestamp) {
        postRef
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
    public String getToken(DatabaseReference postRef, String currentUser) {
        final String[] token = {""};
        postRef
                .child("users")
                .child(currentUser)
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        User u = mutableData.getValue(User.class);
                        if (u == null) {
                            return Transaction.success(mutableData);
                        }
                        token[0] = u.deviceToken;
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                        Log.d(TAG, "postTransaction:onComplete: " + databaseError);
                    }
                });
        return token[0];
    }

}