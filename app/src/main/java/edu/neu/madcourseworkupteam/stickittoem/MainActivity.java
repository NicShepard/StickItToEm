package edu.neu.madcourseworkupteam.stickittoem;

import androidx.appcompat.app.AppCompatActivity;

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

/**
 * Flow:
 *
 * User logs in, their token is added to their username.
 * User types in a username and presses button to send token.
 * Make call to Firebase to get token, and send to that.
 * Give user big alert
 */


public class MainActivity extends AppCompatActivity {


    String token;
    MessagingService messagingService;

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
            }
        });
    }
}