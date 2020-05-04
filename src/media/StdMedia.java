package media;

public class StdMedia implements Media {

    private int duration;
    private String name;

    public StdMedia(int duration, String name) {
        this.duration = duration;
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
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
        mv.visitMedia(this);
    }

    @Override
    public String toString() {
        return "(Media) " +  name + " -- " + duration + " sec";
    }
}
