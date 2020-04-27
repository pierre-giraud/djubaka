package medias;

import visitors.MediaVisitor;

public class Video implements Media {

    private int duration;
    private String title;
    private String resolution;

    public Video(int duration, String title, String resolution) {
        this.duration = duration;
        this.title = title;
        this.resolution = resolution;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public void accept(MediaVisitor mv) {
        mv.visitVideo(this);
    }
}
