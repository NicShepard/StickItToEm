package edu.neu.madcourseworkupteam.stickittoem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RealTimeDatabaseActivity extends AppCompatActivity implements View.OnClickListener {

    // private static final String TAG = RealTimeDatabaseActivity.class.getSimpleName();

    private DatabaseReference database;
    private Button submit;
    private EditText username;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_database);

        //username = (EditText) findViewById(R.id.userName);
        submit = (Button) findViewById(R.id.submit_button2);
        database = FirebaseDatabase.getInstance().getReference();
        test = (TextView) findViewById(R.id.test);

        submit.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.submit_button2:
                // create new user in database
                //database.child("Users").child("Test").setValue(test.getText().toString());
                break;
        }
    }
}