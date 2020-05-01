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

    public void setDuration(int duration) {
        this.duration = duration;
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
    public int getDuration() {
        return duration;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void accept(MediaVisitor mv) {
        mv.visitMusic(this);
    }

    @Override
    public String toString() {
        return "(Music) " + name + " [by " + artist + "] (" + duration + " SEC)";
    }
}
