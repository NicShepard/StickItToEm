package edu.neu.madcourseworkupteam.stickittoem;


import android.view.LayoutInflater;
import android.view.View;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent,
                false);
        return new StickerViewHolder(view);
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        StickerCard currentCard = stickerList.get(position);
        holder.sticker.setImageResource(currentCard.getImageSource());

    }

    @Override
    public int getItemCount() {
        return stickerList.size();
    }
}
