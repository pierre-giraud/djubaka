package media;

public interface Media {
    int getDuration();
    String getName();
    void accept(MediaVisitor mv);
}
