package edu.neu.madcourseworkupteam.stickittoem;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReceivedActivity extends AppCompatActivity {
    private static final String TAG = "Received ACTIVITY";

    private RecyclerView rView;
    private ArrayList<StickerCard> cardList = new ArrayList<>();
    private StickerAdapter stickerAdapter;
    private RecyclerView.LayoutManager layout;
    private String currentUser;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        database = FirebaseDatabase.getInstance().getReference();
        currentUser = getIntent().getStringExtra("CURRENT_USER");
        getEmojisForUser(database, currentUser);


        //FloatingActionButton fab = findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
       // });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        int size = cardList == null ? 0 : cardList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        for (int i = 0; i < size; i++) {
            outState.putInt(KEY_OF_INSTANCE + i + "0", cardList.get(i).getImageSource());
        }
        super.onSaveInstanceState(outState);
    }

    private void init(Bundle savedInstanceState) throws MalformedURLException {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }
    private void initialItemData(Bundle savedInstanceState) throws MalformedURLException {
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (cardList == null || cardList.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);
                for (int i = 0; i < size; i++) {
                    Integer image = savedInstanceState.getInt(KEY_OF_INSTANCE + i + "0");
                    StickerCard sCard = new StickerCard(1);
                    cardList.add(sCard);
                }
            }
        }
        // Load the initial cards
        else {
            StickerCard item1 = new StickerCard(R.drawable.smiley_face);
            StickerCard item2 = new StickerCard(R.drawable.laughing_face);
            //StickerCard item3 = new StickerCard(R.drawable.common_google_signin_btn_icon_light));
            cardList.add(item1);
            cardList.add(item2);
            List<String> emojis = getEmojisForUser(database, currentUser);

            for(String each : emojis) {
                if (each == "smiley") {
                    cardList.add(new StickerCard(R.drawable.smiley_face));
                } else if (each == "laughing") {
                    cardList.add(new StickerCard(R.drawable.laughing_face));
                } else if (each == "angry") {
                    cardList.add(new StickerCard(R.drawable.angry_face));
                } else if (each == "sad") {
                    cardList.add(new StickerCard(R.drawable.sad_face));
                }
            }
        }
    }

    private void createRecyclerView() {

        layout = new LinearLayoutManager(this);
        rView = findViewById(R.id.recyclerView);
        rView.setHasFixedSize(true);

        stickerAdapter = new StickerAdapter(cardList);
        rView.setAdapter(stickerAdapter);
        rView.setLayoutManager(layout);

    }
    private int addItem(int position) {

        cardList.add(position, new StickerCard(R.drawable.common_google_signin_btn_icon_light));
        //        Toast.makeText(LinkCollector.this, "Add an item", Toast.LENGTH_SHORT).show();

        stickerAdapter.notifyItemInserted(position);
        return 1;
    }

    /**
     * Get the emojis for a user
     */
    public List<String> getEmojisForUser(DatabaseReference database, String user) {

        List emojiList = new LinkedList();

        database.child("users").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.e("SNAPSHOT IS", snapshot.getKey());
                    if (snapshot.getKey().equalsIgnoreCase("received")) {
                        for (DataSnapshot receivedMessageUser : snapshot.getChildren()) {
                            if (snapshot.getKey() != null) {
                                for (DataSnapshot message : receivedMessageUser.getChildren()) {
                                    Log.e("ADDING", message.getValue().toString());
                                    emojiList.add(message.getValue().toString());
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error while reading data");
            }
        });

        return emojiList;
    }
}