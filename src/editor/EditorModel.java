package editor;

import exceptions.BadMediaTypeException;
import media.ListMedia;
import observable.Observable;

public interface EditorModel extends Observable {
    void createPlaylist(String name);
    void loadPlaylist(String filename) throws Exception;
    void savePlaylist(String filename) throws Exception;
    void importMedia(String filename) throws Exception;
    void importFolderMedia(String folder) throws Exception;
    void importList(String filename) throws Exception;
    void enterSubList(int num) throws BadMediaTypeException;
    void returnToParentList();
    ListMedia getCurrentList();
    ListMedia getPlaylist();
}
