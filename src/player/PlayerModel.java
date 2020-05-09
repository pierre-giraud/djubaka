package player;

import media.ListMedia;
import media.Media;
import observable.Observable;

public interface PlayerModel extends Observable {
    void startPlaylist() throws Exception;
    void goToNextMedia(Media media) throws Exception;
    void goToPreviousMedia(Media media) throws Exception;
    void goToNextParentMedia() throws Exception;
    void goToPreviousParentMedia() throws Exception;
    Media getCurrentMedia();
    Media getCurrentMediaInfo();
    void setCurrentMediaInfo(Media m);
    ListMedia getPlaylist();
    boolean isFinished();
}
