package editor;

import builders.MediaBuilder;
import exceptions.BadMediaTypeException;
import media.ListMedia;
import observable.Observable;

public interface EditorModel extends Observable {
    void createPlaylist(String name);
    void loadPlaylist(String filename, MediaBuilder builder) throws Exception;
    void savePlaylist(String filename) throws Exception;
    void importMedia(String filename, MediaBuilder builder) throws Exception;
    void importFolderMedia(String folder, MediaBuilder builder) throws Exception;
    void importList(String filename, MediaBuilder builder) throws Exception;
    void enterSubList(int num) throws BadMediaTypeException;
    void returnToParentList();
    ListMedia getCurrentList();
    ListMedia getPlaylist();
}
