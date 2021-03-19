package edu.neu.madcourseworkupteam.stickittoem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Flow:
 *
 * User logs in, their token is added to their username.
 * User types in a username and presses button to send token.
 * Make call to Firebase to get token, and send to that.
 * Give user big alert
 */



public class MainActivity extends AppCompatActivity {

    private static final String SERVER_KEY = "AAAAk1Z2RoU:APA91bHx5coormb5Qv_SeCRUTUpcV9Dz1jAt7HY7pV1gMy8kWdpdRfYDNWy1P_mHVOh6jV11iftmrmaFQHFT2amr0eOIC1VYu2kXtTTml5P78c2LNWGB3GZBSYluqZ_f1gZSKRhsKCTt";
    String token;
    MessagingService messagingService;
    private EditText username;
    private Button go_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.messagingService = new MessagingService();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                Log.e("Token", token);
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_main);

                go_button = (Button) findViewById(R.id.RTDB);
                username = (EditText) findViewById(R.id.UserName);

                go_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: Figure out how to carryover entered username to the RTDB activity
                        Intent intent = new Intent(getApplicationContext(), RealTimeDatabaseActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}