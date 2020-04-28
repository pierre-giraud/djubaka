package editor;

import builders.MediaBuilder;
import exceptions.BadMediaTypeException;
import medias.ListMedia;

public interface EditorModel {
    void createPlaylist(String name);
    void loadPlaylist(String filename, MediaBuilder builder);
    void savePlaylist(String filename);
    void importMedia(String filename, MediaBuilder builder);
    void importFolderMedia(String folder);
    void importList(String filename, MediaBuilder builder);
    void enterSubList(int num) throws BadMediaTypeException;
    void returnToParentList();
    ListMedia getCurrentList();
}
