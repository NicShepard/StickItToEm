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

public class MainActivity extends AppCompatActivity {

//    private static final String SERVER_KEY = "AAAAk1Z2RoU:APA91bHx5coormb5Qv_SeCRUTUpcV9Dz1jAt7HY7pV1gMy8kWdpdRfYDNWy1P_mHVOh6jV11iftmrmaFQHFT2amr0eOIC1VYu2kXtTTml5P78c2LNWGB3GZBSYluqZ_f1gZSKRhsKCTt";

    String instanceId;
    MessagingService messagingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.messagingService = new MessagingService();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                Log.e("Token", token);
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
            }
        });
    }
}