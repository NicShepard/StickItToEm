package edu.neu.madcourseworkupteam.stickittoem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String SERVER_KEY = "AAAAk1Z2RoU:APA91bHx5coormb5Qv_SeCRUTUpcV9Dz1jAt7HY7pV1gMy8kWdpdRfYDNWy1P_mHVOh6jV11iftmrmaFQHFT2amr0eOIC1VYu2kXtTTml5P78c2LNWGB3GZBSYluqZ_f1gZSKRhsKCTt";

    Button go_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go_button = (Button) findViewById(R.id.RTDB);

        go_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RealTimeDatabaseActivity.class);
                startActivity(intent);
            }
        });
    }
}