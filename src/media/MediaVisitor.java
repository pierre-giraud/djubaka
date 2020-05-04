package media;

public interface MediaVisitor {
    void visitMedia(StdMedia m);
    void visitMusic(StdMusic m);
    void visitVideo(StdVideo v);
    void visitList(ListMedia p);
}
