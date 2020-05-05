package player;

import media.ListMedia;
import media.Media;
import observable.ObservableSubject;

public class StdPlayerModel extends ObservableSubject implements PlayerModel {

    private ListMedia listMedia;
    private ListMedia currentList;
    private Media currentMedia;
    private Media currentMediaInfo;

    private boolean finished;

    public StdPlayerModel(ListMedia listMedia){
        super();

        this.listMedia = listMedia;
        this.currentList = this.listMedia;
        finished = false;
    }

    @Override
    public void startPlaylist() {
        if (currentList.getChildren().size() == 0) throw new NullPointerException("This playlist has no media");
        Media m = currentList.getChild(0);

        while (m instanceof ListMedia){
            currentList = (ListMedia) m;
            m = currentList.getChild(0);
        }

        currentMedia = m;
        fireStateChanged();
    }

    @Override
    public void goToNextMedia(Media media) {
        if (currentMedia == null) throw new NullPointerException("Playlist not started");

        int index = currentList.getChildren().indexOf(media);

        if (index < currentList.getChildren().size() - 1){
            Media m = currentList.getChild(index + 1);

            while (m instanceof ListMedia){
                currentList = (ListMedia) m;
                m = currentList.getChild(0);
            }

            currentMedia = m;
            fireStateChanged();
        } else if (currentList.getParent() != null){
            ListMedia l = currentList;
            currentList = currentList.getParent();
            goToNextMedia(l);
        } else {
            finished = true;
            fireStateChanged();
        }
    }

    @Override
    public void goToPreviousMedia(Media media) {
        if (currentMedia == null) throw new NullPointerException("Playlist not started");

        int index = currentList.getChildren().indexOf(media);

        if (index > 0) {
            Media m = currentList.getChild(index - 1);

            while (m instanceof ListMedia){
                currentList = (ListMedia) m;
                m = currentList.getChild(currentList.getChildren().size() - 1);
            }

            currentMedia = m;
            fireStateChanged();
        } else if (currentList.getParent() != null){
            ListMedia l = currentList;
            currentList = currentList.getParent();
            goToPreviousMedia(l);
        }
    }

    @Override
    public void goToNextParentMedia() {
        if (currentMedia == null) throw new NullPointerException("Playlist not started");

        if (currentList.getParent() != null) {
            ListMedia l = currentList;
            currentList = currentList.getParent();
            goToNextMedia(l);
        } else {
            finished = true;
            fireStateChanged();
        }
    }

    @Override
    public void goToPreviousParentMedia() {
        if (currentMedia == null) throw new NullPointerException("Playlist not started");

        if (currentList.getParent() != null){
            ListMedia l = currentList;
            currentList = currentList.getParent();
            goToPreviousMedia(l);
        } else {
            finished = true;
            fireStateChanged();
        }
    }

    @Override
    public Media getCurrentMedia() {
        return currentMedia;
    }

    @Override
    public Media getCurrentMediaInfo() {
        return currentMediaInfo;
    }

    @Override
    public void setCurrentMediaInfo(Media m) {
        currentMediaInfo = m;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public ListMedia getPlaylist() {
        return listMedia;
    }
}
