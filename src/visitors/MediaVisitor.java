package visitors;

import medias.*;

public interface MediaVisitor {
    void visitMusic(Music m);
    void visitVideo(Video v);
    void visitPlaylist(Playlist p);
}