package medias;

import visitors.MediaVisitor;

public interface Media {
    int getDuration();
    String getName();
    void accept(MediaVisitor mv);
}
