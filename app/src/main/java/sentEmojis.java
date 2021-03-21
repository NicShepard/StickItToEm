public class sentEmojis {
    public String emojis = "";

    public sentEmojis(){
        // Default constructor
        this.emojis = "";
    }

    public sentEmojis(String emojis) {
        this.emojis = emojis;
    }

    public String getEmojis() {
        return this.emojis;
    }

    public void addEmojis(String emoji) {
        // append a new sent emoji or set to emoji if empty
        if (this.emojis.isEmpty()) {
            this.emojis = emojis;
        } else {
            this.emojis += emojis;
        }
    }
}
