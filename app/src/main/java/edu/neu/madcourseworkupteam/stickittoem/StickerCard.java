package edu.neu.madcourseworkupteam.stickittoem;

public class StickerCard {
    private String imageSource;

    public StickerCard(String stickerID) {
        this.imageSource = stickerID;
    }

    public String getImageSource() {
        return imageSource;
    }

}
