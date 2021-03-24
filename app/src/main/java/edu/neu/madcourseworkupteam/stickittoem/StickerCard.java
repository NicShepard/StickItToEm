package edu.neu.madcourseworkupteam.stickittoem;

public class StickerCard {
    private int imageSource;
    private String imageID;

    public StickerCard(int stickerID) {
        this.imageSource = stickerID;
    }

    public StickerCard(int stickerID, String ID) {
        this.imageSource = stickerID;
        this.imageID = ID;
    }

    public int getImageSource() {
        return imageSource;
    }

    public String getImageID() {return this.imageID; }

}
