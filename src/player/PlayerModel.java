package player;

import media.ListMedia;
import media.Media;
import observable.Observable;

public interface PlayerModel extends Observable {
    void startPlaylist();
    void goToNextMedia(Media media);
    void goToPreviousMedia(Media media);
    void goToNextParentMedia();
    void goToPreviousParentMedia();
    Media getCurrentMedia();
    Media getCurrentMediaInfo();
    void setCurrentMediaInfo(Media m);
    ListMedia getPlaylist();
    boolean isFinished();
}
