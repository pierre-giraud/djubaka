package media;

public class StdVideo implements Video {

    private int duration;
    private String name;
    private String resolution;

    public StdVideo(int duration, String name, String resolution) {
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

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public String getResolution() {
        return resolution;
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

    @Override
    public String toString() {
        return "(Video) " + name + " [displayed in " + resolution + "] (" + duration + " sec)";
    }
}
