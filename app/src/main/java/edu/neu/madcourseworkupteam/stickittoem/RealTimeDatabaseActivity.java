package edu.neu.madcourseworkupteam.stickittoem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Date;
import java.util.Map;

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
    private ImageButton starEmoji;
    private ImageButton crossEmoji;
    private ImageButton plusEmoji;
    private ImageButton lockEmoji;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_database);

        userName = (TextView) findViewById(R.id.username);
        sendToFriend = (EditText) findViewById(R.id.sendToUser);

        database = FirebaseDatabase.getInstance().getReference();

        starEmoji = (ImageButton) findViewById(R.id.star);
        crossEmoji = (ImageButton) findViewById(R.id.cross);
        plusEmoji = (ImageButton) findViewById(R.id.plus);
        lockEmoji = (ImageButton) findViewById(R.id.lock);

        starEmoji.setOnClickListener(this::onClick);
        crossEmoji.setOnClickListener(this::onClick);
        plusEmoji.setOnClickListener(this::onClick);
        lockEmoji.setOnClickListener(this::onClick);

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
            database.child("users").child(user.username).child("deviceToken").setValue(token);

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
            // emoji 1
            case R.id.star:
                // TODO: figure out how to get current user id
                getToken(database, "nic");
//                RealTimeDatabaseActivity.this.onSendEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "star", timestamp);
//                RealTimeDatabaseActivity.this.onReceiveEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "star", timestamp);
                break;
            // emoji 2
            case R.id.cross:
                // TODO: figure out how to get current user id
                RealTimeDatabaseActivity.this.onSendEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "cross", timestamp);
                RealTimeDatabaseActivity.this.onReceiveEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "cross", timestamp);
                break;
            // emoji 3
            case R.id.plus:
                // TODO: figure out how to get current user id
                RealTimeDatabaseActivity.this.onSendEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "plus", timestamp);
                RealTimeDatabaseActivity.this.onReceiveEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "plus", timestamp);
                break;
            // emoji 4
            case R.id.lock:
                // TODO: figure out how to get current user id
                RealTimeDatabaseActivity.this.onSendEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "lock", timestamp);
                RealTimeDatabaseActivity.this.onReceiveEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "lock", timestamp);
                break;
        }
    }

    /**
     * @param database
     * @param currentUser
     */
    private void onSendEmoji(DatabaseReference database, String currentUser, String otherUser, String emoji, String timestamp) {
        database
                .child("users")
                .child(currentUser)
                .child("sent")
                .child(otherUser)
                .child(timestamp)
                .setValue(emoji);
    }

    /**
     * @param database
     * @param currentUser
     */
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
    public void getToken(DatabaseReference database, String user) {

        final String key = null;

        Query query = database.child("users").child(user).orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                   if(ds.child("deviceKey").getValue(String.class) != null) {
                       Map<String, Object> map = (Map<String, Object>) ds.getValue();
                       Log.e("TAG", map.toString());
                   };
                }
//                User value = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error while reading data");
            }
        });
    }
}