package edu.neu.madcourseworkupteam.stickittoem;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class ReceivedActivity extends AppCompatActivity {
    private RecyclerView rView;
    private ArrayList<StickerCard> cardList = new ArrayList<>();
    private StickerAdapter stickerAdapter;
    private RecyclerView.LayoutManager layout;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        try {
            init(savedInstanceState);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
}