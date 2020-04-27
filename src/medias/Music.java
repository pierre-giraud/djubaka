package medias;

import visitors.MediaVisitor;

public class Music implements Media {

    private int duration;
    private String name;
    private String artist;

    public Music(int duration, String name, String artist) {
        this.duration = duration;
        this.name = name;
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public void accept(MediaVisitor mv) {
        mv.visitMusic(this);
    }
}
