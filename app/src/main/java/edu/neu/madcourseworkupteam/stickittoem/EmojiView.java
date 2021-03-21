package edu.neu.madcourseworkupteam.stickittoem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EmojiView extends AppCompatActivity {
    private EditText findUser;
    private Button searchUser;
    private Button smile;
    private Button sad;
    private Button frown;
    private Button laugh;
    private Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji_view);
    }
}