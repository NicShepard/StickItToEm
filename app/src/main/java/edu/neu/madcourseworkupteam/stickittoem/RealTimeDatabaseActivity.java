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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

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

        // TODO: on create, check if username is already in database
        //  if it is, read all the info from database and load it up
        //  otherwise write to database new user

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
            // for username, get what the user typed in from the login
            // instead of score, should be the device id/token
            userName.setText("nberg"); ;
            User user = new User(userName.getText().toString(), token);
            database.child("users").child(user.username).setValue(user);

        } catch (Exception e) {
            Log.d("Failed to complete token refresh", String.valueOf(e));
        }

        database.child("users").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        User user = dataSnapshot.getValue(User.class);

                        // if the key is current user?
                        /*
                        if (dataSnapshot.getKey().equalsIgnoreCase("user1")) {
                            score.setText(user.score);
                            userName.setText(user.username);
                        }*/
                        Log.e(TAG, "onChildAdded: dataSnapshot = " + dataSnapshot.getValue());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        User user = dataSnapshot.getValue(User.class);

                        // if the key is current user?
                        /*
                        if (dataSnapshot.getKey().equalsIgnoreCase("user1")) {
                            score.setText(user.score);
                            userName.setText(user.username);
                        }*/
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
        switch(view.getId()) {
            // emoji 1
            case R.id.star:
                // TODO: figure out how to get current user id
                RealTimeDatabaseActivity.this.onSendEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "star");
                RealTimeDatabaseActivity.this.onReceiveEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "star");
                break;
            // emoji 2
            case R.id.cross:
                // TODO: figure out how to get current user id
                RealTimeDatabaseActivity.this.onSendEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "cross");
                RealTimeDatabaseActivity.this.onReceiveEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "cross");
                break;
            // emoji 3
            case R.id.plus:
                // TODO: figure out how to get current user id
                RealTimeDatabaseActivity.this.onSendEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "plus");
                RealTimeDatabaseActivity.this.onReceiveEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "plus");
                break;
            // emoji 4
            case R.id.lock:
                // TODO: figure out how to get current user id
                RealTimeDatabaseActivity.this.onSendEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "lock");
                RealTimeDatabaseActivity.this.onReceiveEmoji(database, userName.getText().toString(), sendToFriend.getText().toString(), "lock");
                break;
        }
    }

    /**
     *
     * @param postRef
     * @param currentUser
     */
    private void onSendEmoji(DatabaseReference postRef, String currentUser, String otherUser, String emoji) {
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

                        // on button press, update sent child under current
                        // user to include friend sent to and the emoji
                        u.sendEmoji(otherUser, emoji);

                        mutableData.setValue(u);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                        Log.d(TAG, "postTransaction:onComplete: " + databaseError);
                    }
                });
    }

    /**
     *
     * @param postRef
     * @param currentUser
     */
    private void onReceiveEmoji(DatabaseReference postRef, String currentUser, String otherUser, String emoji) {
        postRef
                .child("users")
                .child(otherUser)
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        User u = mutableData.getValue(User.class);
                        if (u == null) {
                            return Transaction.success(mutableData);
                        }

                        // on button press, update sent child under current
                        // user to include friend sent to and the emoji
                        u.receiveEmoji(currentUser, emoji);

                        mutableData.setValue(u);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                        Log.d(TAG, "postTransaction:onComplete: " + databaseError);
                    }
                });
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


    // Read and Write from/to database
    public void doAddDataToDb(View view) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("message");

        ref.setValue("Hello World!");

        // Reads from the database
        ref.addValueEventListener(new ValueEventListener() {
            // triggered once the listener is attached and again every time the data changes
            // including the children
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "OnDataChange Value is: " + value);
                //TextView text = (TextView) findViewById(R.id.dataUpdateTextView);
                //text.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
}