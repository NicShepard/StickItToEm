package edu.neu.madcourseworkupteam.stickittoem;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StickerViewHolder extends RecyclerView.ViewHolder {
    public ImageView sticker;
    public StickerViewHolder(@NonNull View itemView) {
        super(itemView);
        sticker = itemView.findViewById(R.id.sticker_image);
        //TODO: Set sticker to findViewById w.e it's called
    }
}
