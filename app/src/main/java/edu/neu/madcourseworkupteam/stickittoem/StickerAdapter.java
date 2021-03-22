package edu.neu.madcourseworkupteam.stickittoem;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StickerAdapter extends RecyclerView.Adapter<StickerViewHolder> {
    private final ArrayList<StickerCard> stickerList;

    public StickerAdapter(ArrayList<StickerCard> cards) { this.stickerList = cards; }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stickerList.size();
    }
}
