package medias;

import visitors.MediaVisitor;

public interface Media {
    void accept(MediaVisitor mv);
}
