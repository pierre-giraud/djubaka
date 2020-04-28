package medias;

import visitors.MediaVisitor;

public class Video implements Media {

    private int duration;
    private String name;
    private String resolution;

    public Video(int duration, String name, String resolution) {
        this.duration = duration;
        this.name = name;
        this.resolution = resolution;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void accept(MediaVisitor mv) {
        mv.visitVideo(this);
    }
}
