package edu.neu.madcourseworkupteam.stickittoem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RealTimeDatabaseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RealTimeDatabaseActivity.class.getSimpleName();

    private DatabaseReference database;
    private TextView userName;
    private TextView score;
    private TextView userName2;
    private TextView score2;
    private RadioButton player1;
    private Button add5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_database);

        userName = (TextView) findViewById(R.id.username);
        userName2 = (TextView) findViewById(R.id.username2);
        score = (TextView) findViewById(R.id.score);
        score2 = (TextView) findViewById(R.id.score2);

        player1 = (RadioButton) findViewById(R.id.player1);

        database = FirebaseDatabase.getInstance().getReference();

        add5 = (Button) findViewById(R.id.add5);

        add5.setOnClickListener(this::onClick);

        database.child("users").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        User user = dataSnapshot.getValue(User.class);

                        if (dataSnapshot.getKey().equalsIgnoreCase("user1")) {
                            score.setText(user.score);
                            userName.setText(user.username);
                        } else {
                            score2.setText(String.valueOf(user.score));
                            userName2.setText(user.username);
                        }
                        Log.e(TAG, "onChildAdded: dataSnapshot = " + dataSnapshot.getValue());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        User user = dataSnapshot.getValue(User.class);

                        if (dataSnapshot.getKey().equalsIgnoreCase("user1")) {
                            score.setText(user.score);
                            userName.setText(user.username);
                        } else {
                            score2.setText(String.valueOf(user.score));
                            userName2.setText(user.username);
                        }
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
            case R.id.add5:
                RealTimeDatabaseActivity.this.onAddScore(database, player1.isChecked() ? "user1" : "user2");
                break;
        }
    }

    private void onAddScore(DatabaseReference postRef, String user) {
        postRef
                .child("users")
                .child(user)
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        User u = mutableData.getValue(User.class);
                        if (u == null) {
                            return Transaction.success(mutableData);
                        }

                        u.score = String.valueOf(Integer.valueOf(u.score) + 5);

                        mutableData.setValue(u);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                        Log.d(TAG, "postTransaction:onComplete: " + databaseError);
                    }
                });
    }

    public void resetUsers(View view) {
        User user = new User("user1", "0");
        database.child("users").child(user.username).setValue(user);

        User user2 = new User("user2", "0");
        database.child("users").child(user2.username).setValue(user2);
    }

    public void doAddDataToDb(View view) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("message");

        ref.setValue("Hello World!");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                TextView text = (TextView) findViewById(R.id.dataUpdateTextView);
                text.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
}