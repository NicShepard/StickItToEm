package edu.neu.madcourseworkupteam.stickittoem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
 * Our MainActivity is where users log in. A toast displays their device key
 * in case it can be helpful for debugging.
 */
public class MainActivity extends AppCompatActivity {

    String token;
    private Button go_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the token and log it for debugging
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                setContentView(R.layout.activity_main);

                //Get token
                token = instanceIdResult.getToken();
                Log.d("Token", token);

                //Get views
                go_button = (Button) findViewById(R.id.RTDB);
                TextView currentUser = findViewById(R.id.UserName);

                //Carry the name of the user into the second screen
                go_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), RealTimeDatabaseActivity.class);
                        intent.putExtra("CURRENT_USER", currentUser.getText().toString());
                        startActivity(intent);
                    }
                });
            }
        });
    }
}